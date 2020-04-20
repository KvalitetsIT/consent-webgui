package dk.kvalitetsit.consentservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import dk.kvalitetsit.consentservice.entity.Municipality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.kvalitetsit.consentservice.dto.AddConsentTemplateRequest;
import dk.kvalitetsit.consentservice.dto.ConsentDTO;
import dk.kvalitetsit.consentservice.dto.ConsentDTOs;
import dk.kvalitetsit.consentservice.dto.ConsentNotification.Function;
import dk.kvalitetsit.consentservice.dto.ConsentStatus;
import dk.kvalitetsit.consentservice.dto.ConsentStatus.Status;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateTO;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateTOs;
import dk.kvalitetsit.consentservice.dto.UpdateConsentTemplateRequest;
import dk.kvalitetsit.consentservice.entity.Consent;
import dk.kvalitetsit.consentservice.entity.ConsentTemplate;
import dk.kvalitetsit.consentservice.repository.ConsentRepository;
import dk.kvalitetsit.consentservice.repository.ConsentTemplateRepository;

@Service
public class ConsentService {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);
	
	@Autowired
	ConsentRepository consentRepository;
	
	@Autowired
	ConsentTemplateRepository consentTemplateRepository;
	
	@Autowired
	ConsentNotificationService notificationService;

	@Autowired
    MunicipalityService municipalityService;
	
	public ConsentStatus getConsentStatus(String userId,Integer MunicipalityId, String appId) throws ConsentServiceException {
		LOGGER.info("Checking consent for: " + appId);
        Optional<Municipality> municipality = Optional.empty();
		if(MunicipalityId != null) {
            municipality = municipalityService.getMunicipality(MunicipalityId);
        }
        if(!municipality.isPresent()) {
            //Try to look up municipality from previous consents
            List<Consent> byCitizenId = consentRepository.findByCitizenId(userId);
            List<Municipality> municipalities = byCitizenId.stream().map(c -> c.getMunicipality()).distinct().collect(Collectors.toList());
            if(municipalities.size() == 1) {
                municipality = Optional.of(municipalities.get(0));
            }
        }
        ConsentTemplate template = getActiveForAppId(appId,municipality.map(Municipality::getId).orElse(0));
		if (template == null) {
			//No consent configured for this appid - allow user to continue;
			ConsentStatus rv = new ConsentStatus();
			rv.setStatus(Status.ACCEPTED);
			return rv;
		}
		Consent consent = getConsentForTemplateAndUser(template, userId);
		ConsentStatus rv = new ConsentStatus();
		if (consent == null) {
			rv.setStatus(Status.UNANSWERED);
			//we do not currently have consent for appId - but we may have given consent for an older versionconfig
			//we need special status for this
			List<ConsentTemplate> oldTemplates = consentTemplateRepository.findByAppIdAndMunicipalityIdAndActive(appId,municipality.map(Municipality::getId).orElse(0),false);
			for (ConsentTemplate oldTemplate : oldTemplates) {
				Consent oldConsent = getConsentForTemplateAndUser(oldTemplate, userId);
				if (oldConsent != null && oldConsent.isAnswer()) {
					rv.setStatus(Status.DEPRECATED);
				}
			}
			
			rv.setTemplateContent(template.getContent());
			rv.setTemplateMimeType(template.getMimeType());
			rv.setFriendlyName(template.getFriendlyName());
		} else {
			if (consent.isAnswer()) {
				rv.setStatus(Status.ACCEPTED);
			} else {
				rv.setStatus(Status.NOT_ACCEPTED);				
			}
		}
		return rv;
	}
	
	public void setConsentStatus(String userId, Integer municipalityId, String appId, boolean consent) throws ConsentServiceException {
        Optional<Municipality> municipality = municipalityService.getMunicipality(municipalityId);
        ConsentTemplate template = getTemplate(appId,municipality.map(Municipality::getId).orElse(0));
		Consent consentFromDb = getConsentForTemplateAndUser(template, userId);
		if (consentFromDb != null) {
			consentFromDb.setAnswer(consent);
			consentRepository.save(consentFromDb);
		} else {
			Consent newConsent = new Consent();
			newConsent.setAnswer(consent);
			newConsent.setConsentTemplate(template);
			newConsent.setMunicipality(municipality.orElse(null));
			newConsent.setCitizenId(userId);
			newConsent.setCreationDate(new Date());
			consentRepository.save(newConsent);
		}
		
		if (consent) {
			notificationService.sendNotification(userId, template.getNotificationSubject(), template.getMimeType(), template.getContent(), Function.AFGIVELSE);
		}
	}
	
	private ConsentTemplate getTemplate(String appId,int municipalityId) throws ConsentServiceException {
		ConsentTemplate template = getActiveForAppId(appId,municipalityId);
		if (template != null) {
			return template;
		} else {
			throw new ConsentServiceException("No consentTemplate for appid = " + appId);
		}
	}
	
	private Consent getConsentForTemplateAndUser(ConsentTemplate template, String userId) {
		return consentRepository.findByConsentTemplateAndCitizenIdAndRevocationDate(template, userId,null);
	}

	public void addConsentTemplate(AddConsentTemplateRequest addConsentTemplateRequest) throws ConsentServiceException {
		ConsentTemplate template = new ConsentTemplate();
		
		if (isBlank(addConsentTemplateRequest.getAppId())) { throw new ConsentServiceException("appId must be set"); }
		if (isBlank(addConsentTemplateRequest.getContent())) { throw new ConsentServiceException("content must be set"); }
		if (isBlank(addConsentTemplateRequest.getFriendlyName())) { throw new ConsentServiceException("friendlyName must be set"); }
		if (isBlank(addConsentTemplateRequest.getMimeType())) { throw new ConsentServiceException("mimeType must be set"); }
		if (isBlank(addConsentTemplateRequest.getNotificationSubject())) { throw new ConsentServiceException("notificationSubject must be set"); }		
		if (isBlank(addConsentTemplateRequest.getMunicipalityId())) { throw new ConsentServiceException("municipalityID must be set"); }
        Municipality municipality = municipalityService.findByName(addConsentTemplateRequest.getMunicipalityId());
        if (municipality == null) { throw new ConsentServiceException("Municipality: "+addConsentTemplateRequest.getMunicipalityId()+" not found"); }
        if (getActiveForAppId(addConsentTemplateRequest.getAppId(),municipality.getId()) != null ) { throw new ConsentServiceException("appId already exists, use updateConsentTemplate instead"); }
		
		template.setActive(true);
		template.setVersion(1);
		template.setMunicipality(municipality);
		template.setAppId(addConsentTemplateRequest.getAppId());
		template.setContent(addConsentTemplateRequest.getContent());
		template.setFriendlyName(addConsentTemplateRequest.getFriendlyName());
		template.setMimeType(addConsentTemplateRequest.getMimeType());
		template.setNotificationSubject(addConsentTemplateRequest.getNotificationSubject());
		
		consentTemplateRepository.save(template);
	}

	public void updateConsentTemplate(UpdateConsentTemplateRequest updateConsentTemplateRequest) throws ConsentServiceException {
		
		if (isBlank(updateConsentTemplateRequest.getAppId())) { throw new ConsentServiceException("appId must be set"); }
		if (isBlank(updateConsentTemplateRequest.getContent())) { throw new ConsentServiceException("content must be set"); }
		if (isBlank(updateConsentTemplateRequest.getMimeType())) { throw new ConsentServiceException("mimeType must be set"); }
		if (isBlank(updateConsentTemplateRequest.getMunicipality())) { throw new ConsentServiceException("municipality must be set"); }
        Municipality municipality = municipalityService.findByName(updateConsentTemplateRequest.getMunicipality());
        if (municipality == null) { throw new ConsentServiceException("Municipality: "+updateConsentTemplateRequest.getMunicipality()+" not found"); }

		ConsentTemplate oldTemplate = getTemplate(updateConsentTemplateRequest.getAppId(),municipality.getId());
		
		oldTemplate.setActive(false);
		
		ConsentTemplate newTemplate = new ConsentTemplate();
		
		newTemplate.setActive(true);
		newTemplate.setVersion(oldTemplate.getVersion()+1);
		newTemplate.setAppId(oldTemplate.getAppId());
		newTemplate.setContent(updateConsentTemplateRequest.getContent());
		newTemplate.setFriendlyName(oldTemplate.getFriendlyName());
		newTemplate.setMimeType(updateConsentTemplateRequest.getMimeType());
		newTemplate.setNotificationSubject(oldTemplate.getNotificationSubject());
		newTemplate.setMunicipality(oldTemplate.getMunicipality());
		consentTemplateRepository.save(oldTemplate);
		consentTemplateRepository.save(newTemplate);
		
	}	
	
	private List<ConsentTemplate> getActiveForAppId(String appId) throws ConsentServiceException {
	    return consentTemplateRepository.findByAppIdAndActive(appId,true);
    }

	private ConsentTemplate getActiveForAppId(String appId,int municipalityId) throws ConsentServiceException {
		List<ConsentTemplate> templates = consentTemplateRepository.findByAppIdAndMunicipalityIdAndActive(appId,municipalityId,true);
		if (templates.size() == 1) {
			return templates.get(0);
		} else if (templates.size() == 0) {
			return null;
		} else {
			throw new ConsentServiceException("More than one active template for appid = " + appId);
		}
	}
	
	private boolean isBlank(String s) {
		return (s==null || s.trim().length() == 0);
	}

	public ConsentDTOs getAllConsents(String citizenId) {
		LOGGER.info("Attempting to fetch all consents for citizenId: " + citizenId);
		List<Consent> cs = consentRepository.findByCitizenId(citizenId);
		List<ConsentDTO> rv = new ArrayList<>();
		for (Consent c : cs) {
			ConsentDTO cdto = new ConsentDTO();
			cdto.setAppName(c.getConsentTemplate().getFriendlyName());
			cdto.setCreationDate(c.getCreationDate());
			cdto.setRevocationDate(c.getRevocationDate());
			cdto.setId(c.getId());
			cdto.setTemplateId(c.getConsentTemplate().getId());
			cdto.setTemplateActive(c.getConsentTemplate().isActive());
			cdto.setMunicipality(c.getMunicipality().getName());
			rv.add(cdto);
		}
		ConsentDTOs dtos = new ConsentDTOs();
		dtos.setList(rv);
		return dtos;
	}

	public void revokeConsent(String userId, long consentId) throws ConsentServiceException {
		Optional<Consent> oc = consentRepository.findById(consentId);
		if (!oc.isPresent()) {
			throw new ConsentServiceException("Consent does not exist");
		}
		Consent c = oc.get();
		if (!c.getCitizenId().equals(userId)) {
			throw new ConsentServiceException("Consent does not belong to current user");
		}
		c.setRevocationDate(new Date());
		notificationService.sendNotification(userId, c.getConsentTemplate().getNotificationSubject(), c.getConsentTemplate().getMimeType(), c.getConsentTemplate().getContent(), Function.TILBAGETRAEKNING);
		consentRepository.save(c);
	}

	public ConsentTemplateDTO getConsentTemplate(long consentTemplateId) {
		Optional<ConsentTemplate> ct = consentTemplateRepository.findById(consentTemplateId);
		if(ct.isPresent()) {
            ConsentTemplateDTO rv = new ConsentTemplateDTO();
            rv.setMimeType(ct.get().getMimeType());
            rv.setContent(ct.get().getContent());
            return rv;
        }
		return null;
	}

	public ConsentTemplateTOs getAllActiveConsentTemplates() {
		List<ConsentTemplate> allTemplates = consentTemplateRepository.findByActive(true);
		List<ConsentTemplateTO> rvlist = new ArrayList<>();
		for (ConsentTemplate t : allTemplates) {
			ConsentTemplateTO ctto = new ConsentTemplateTO();
			ctto.setAppId(t.getAppId());
			ctto.setFriendlyName(t.getFriendlyName());
			ctto.setVersion(t.getVersion());
			ctto.setNotificationSubject(t.getNotificationSubject());
			ctto.setId(t.getId());
			ctto.setMunicipality(t.getMunicipality().getName());
			rvlist.add(ctto);
		}
		
		ConsentTemplateTOs rv = new ConsentTemplateTOs();
		rv.setList(rvlist);
		
		return rv;
		
	}
}
