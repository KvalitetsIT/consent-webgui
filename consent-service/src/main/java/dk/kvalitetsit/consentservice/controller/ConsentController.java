package dk.kvalitetsit.consentservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.kvalitetsit.consentservice.dto.ConsentStatus;
import dk.kvalitetsit.consentservice.dto.UpdateConsent;

@RestController
public class ConsentController extends AbstractController {

	Map<String, String> appConsentMap = new HashMap<>();
	
	Map<String, Boolean> consent = new HashMap<>();
	
	@PostConstruct
	public void init() {
		appConsentMap.put("dev:kit:appa", "82371231");
		appConsentMap.put("dev:kit:appb", "44125253");
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.GET)
	public ConsentStatus getConsentStatus(@RequestParam("userId") String userId, @RequestParam("appId") String appId) {
		
		ConsentStatus cs = new ConsentStatus();
		cs.setConsentTemplateId(appConsentMap.get(appId));
		cs.setConsent(consent.get(appId+userId));
		
		return cs;
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.POST)
	public void setConsentStatus(@RequestBody UpdateConsent updateConsent) {

		consent.put(updateConsent.getAppId()+updateConsent.getUserId(), updateConsent.getConsent());
	}
}
