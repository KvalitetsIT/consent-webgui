package dk.kvalitetsit.consentservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public ConsentStatus getConsentStatus(String userId, String appId) throws ConsentServiceException {
		LOGGER.info("Checking consent for: " + appId);
		ConsentTemplate template = getActiveForAppId(appId);
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
			//we do not currently have consent for appId - but we may have given consent for an older version
			//we need special status for this
			List<ConsentTemplate> oldTemplates = consentTemplateRepository.findByAppIdAndActive(appId,false);
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
	
	public void setConsentStatus(String userId, String appId, boolean consent) throws ConsentServiceException {
		ConsentTemplate template = getTemplate(appId);
		Consent consentFromDb = getConsentForTemplateAndUser(template, userId);
		if (consentFromDb != null) {
			consentFromDb.setAnswer(consent);
			consentRepository.save(consentFromDb);
		} else {
			Consent newConsent = new Consent();
			newConsent.setAnswer(consent);
			newConsent.setConsentTemplate(template);
			newConsent.setCitizenId(userId);
			consentRepository.save(newConsent);
		}
		
		if (consent) {
			notificationService.sendNotification(userId, template.getNotificationSubject(), template.getMimeType(), template.getContent(), Function.AFGIVELSE);
		}
	}
	
	private ConsentTemplate getTemplate(String appId) throws ConsentServiceException {
		ConsentTemplate template = getActiveForAppId(appId);
		if (template != null) {
			return template;
		} else {
			throw new ConsentServiceException("No consentTemplate for appid = " + appId);
		}
	}
	
	private Consent getConsentForTemplateAndUser(ConsentTemplate template, String userId) {
		return consentRepository.findByConsentTemplateAndCitizenId(template, userId);
	}

	public void addConsentTemplate(AddConsentTemplateRequest addConsentTemplateRequest) throws ConsentServiceException {
		ConsentTemplate template = new ConsentTemplate();
		
		if (isBlank(addConsentTemplateRequest.getAppId())) { throw new ConsentServiceException("appId must be set"); }
		if (isBlank(addConsentTemplateRequest.getContent())) { throw new ConsentServiceException("content must be set"); }
		if (isBlank(addConsentTemplateRequest.getFriendlyName())) { throw new ConsentServiceException("friendlyName must be set"); }
		if (isBlank(addConsentTemplateRequest.getMimeType())) { throw new ConsentServiceException("mimeType must be set"); }
		if (isBlank(addConsentTemplateRequest.getNotificationSubject())) { throw new ConsentServiceException("notificationSubject must be set"); }		
		if (getActiveForAppId(addConsentTemplateRequest.getAppId()) != null ) { throw new ConsentServiceException("appId already exists, use updateConsentTemplate instead"); }
		
		template.setActive(true);
		template.setVersion(1);
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
		
		ConsentTemplate oldTemplate = getTemplate(updateConsentTemplateRequest.getAppId());
		
		oldTemplate.setActive(false);
		
		ConsentTemplate newTemplate = new ConsentTemplate();
		
		newTemplate.setActive(true);
		newTemplate.setVersion(oldTemplate.getVersion()+1);
		newTemplate.setAppId(oldTemplate.getAppId());
		newTemplate.setContent(updateConsentTemplateRequest.getContent());
		newTemplate.setFriendlyName(oldTemplate.getFriendlyName());
		newTemplate.setMimeType(updateConsentTemplateRequest.getMimeType());
		newTemplate.setNotificationSubject(oldTemplate.getNotificationSubject());
		
		consentTemplateRepository.save(oldTemplate);
		consentTemplateRepository.save(newTemplate);
		
	}	
	
	private ConsentTemplate getActiveForAppId(String appId) throws ConsentServiceException {
		List<ConsentTemplate> templates = consentTemplateRepository.findByAppIdAndActive(appId,true);
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
		List<Consent> cs = consentRepository.findByCitizenId(citizenId);
		List<ConsentDTO> rv = new ArrayList<>();
		for (Consent c : cs) {
			ConsentDTO cdto = new ConsentDTO();
			cdto.setAppName(c.getConsentTemplate().getFriendlyName());
			cdto.setCreationDate(c.getCreationDate());
			cdto.setRevocationDate(c.getRevocationDate());
			cdto.setId(c.getId());
			cdto.setTemplateId(c.getConsentTemplate().getId());
			rv.add(cdto);
		}
		ConsentDTOs dtos = new ConsentDTOs();
		dtos.setList(rv);
		return dtos;
	}

	public void revokeConsent(String userId, long consentId) {
		Consent c = consentRepository.findOne(consentId);
		c.setRevocationDate(new Date());
		consentRepository.save(c);
	}

	public ConsentTemplateDTO getConsentTemplate(long consentTemplateId) {
		ConsentTemplate ct = consentTemplateRepository.findOne(consentTemplateId);
		ConsentTemplateDTO rv = new ConsentTemplateDTO();
		rv.setMimeType(ct.getMimeType());
		rv.setContent(ct.getContent());
		return rv;
	}
}
