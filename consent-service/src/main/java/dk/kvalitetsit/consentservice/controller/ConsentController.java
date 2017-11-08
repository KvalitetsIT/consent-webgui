package dk.kvalitetsit.consentservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.kvalitetsit.consentservice.dto.AddConsentTemplateRequest;
import dk.kvalitetsit.consentservice.dto.ConsentDTOs;
import dk.kvalitetsit.consentservice.dto.ConsentStatus;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;
import dk.kvalitetsit.consentservice.dto.SessionData;
import dk.kvalitetsit.consentservice.dto.UpdateConsent;
import dk.kvalitetsit.consentservice.dto.UpdateConsentTemplateRequest;
import dk.kvalitetsit.consentservice.service.ConsentService;
import dk.kvalitetsit.consentservice.service.ConsentServiceException;
import dk.kvalitetsit.consentservice.service.UserService;

@RestController
public class ConsentController extends AbstractController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConsentController.class);
	
	@Autowired
	private ConsentService service;
	
	@Autowired
	private UserService userService;
	
	@Value("${uid.key}")
	private String uidKey;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.GET) 
	public ConsentStatus getConsentStatus(@RequestParam("userId") String userId, @RequestParam("appId") String appId) throws ConsentServiceException {		
		return service.getConsentStatus(userId, appId);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.POST)
	public void setConsentStatus(@RequestBody UpdateConsent updateConsent) throws ConsentServiceException {
		service.setConsentStatus(updateConsent.getUserId(), updateConsent.getAppId(), updateConsent.getConsent());
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/addConsentTemplate", method = RequestMethod.POST)
	public void addConsentTemplate(@RequestBody AddConsentTemplateRequest addConsentTemplateRequest) throws ConsentServiceException {
		service.addConsentTemplate(addConsentTemplateRequest);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/updateConsentTemplate", method = RequestMethod.POST)
	public void updateConsentTemplate(@RequestBody UpdateConsentTemplateRequest updateConsentTemplateRequest) throws ConsentServiceException {
		service.updateConsentTemplate(updateConsentTemplateRequest);
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllConsents", method = RequestMethod.GET) 
	public ConsentDTOs getAllConsents() throws ConsentServiceException {	
		LOGGER.info("Received getAllConsents call");
		String uid = getUid();
		LOGGER.info("uid is " + uid);
		return service.getAllConsents(getUid());
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/revokeConsent", method = RequestMethod.GET) 
	public void revokeConsent(@RequestParam("consentId") long consentId) throws ConsentServiceException {		
		service.revokeConsent(getUid(), consentId);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getConsentTemplate", method = RequestMethod.GET)
	public ConsentTemplateDTO getConsentTemplate(@RequestParam("consentTemplateId") long consentTemplateId) throws ConsentServiceException {
		return service.getConsentTemplate(consentTemplateId);
	}
	
	private String getUid() {
		SessionData data = userService.getSessionData();		
		String rv = data.getUserAttributes().get(uidKey).get(0);
		return rv;
	}		
	
}
