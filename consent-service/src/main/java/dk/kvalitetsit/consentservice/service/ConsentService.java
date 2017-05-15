package dk.kvalitetsit.consentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dk.kvalitetsit.consentservice.dto.ConsentStatus;
import dk.kvalitetsit.consentservice.dto.ConsentStatus.Status;
import dk.kvalitetsit.consentservice.entity.Consent;
import dk.kvalitetsit.consentservice.entity.ConsentTemplate;
import dk.kvalitetsit.consentservice.repository.ConsentRepository;
import dk.kvalitetsit.consentservice.repository.ConsentTemplateRepository;

@Service
public class ConsentService {

	@Autowired
	ConsentRepository consentRepository;
	
	@Autowired
	ConsentTemplateRepository consentTemplateRepository;
	
	public ConsentStatus getConsentStatus(String userId, String appId) throws ConsentServiceException {
		ConsentTemplate template = getTemplate(appId);
		Consent consent = getConsentForTemplateAndUser(template, userId);
		ConsentStatus rv = new ConsentStatus();
		if (consent == null) {
			rv.setStatus(Status.UNANSWERED);
			rv.setTemplateContent(template.getContent());
			rv.setTemplateMimeType(template.getMimeType());
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
	}
	
	private ConsentTemplate getTemplate(String appId) throws ConsentServiceException {
		ConsentTemplate template = consentTemplateRepository.findByAppId(appId);
		if (template != null) {
			return template;
		} else {
			throw new ConsentServiceException("No consentTemplate for appid = " + appId);
		}
	}
	
	private Consent getConsentForTemplateAndUser(ConsentTemplate template, String userId) {
		return consentRepository.findByConsentTemplateAndCitizenId(template, userId);
	}	
}
