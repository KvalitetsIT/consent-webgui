package dk.kvalitetsit.consentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.kvalitetsit.consentservice.dto.ConsentStatus;
import dk.kvalitetsit.consentservice.dto.UpdateConsent;
import dk.kvalitetsit.consentservice.service.ConsentService;
import dk.kvalitetsit.consentservice.service.ConsentServiceException;

@RestController
public class ConsentController extends AbstractController {
	
	@Autowired
	private ConsentService service;
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.GET) 
	public ConsentStatus getConsentStatus(@RequestParam("userId") String userId, @RequestParam("appId") String appId) throws ConsentServiceException {		
		return service.getConsentStatus(userId, appId);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.POST)
	public void setConsentStatus(@RequestBody UpdateConsent updateConsent) throws ConsentServiceException {
		service.setConsentStatus(updateConsent.getUserId(), updateConsent.getAppId(), updateConsent.getConsent());
	}
}
