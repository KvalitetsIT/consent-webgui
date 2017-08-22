package dk.kvalitetsit.consent.admingui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consentservice.dto.AddConsentTemplateRequest;
import dk.kvalitetsit.consentservice.dto.UpdateConsentTemplateRequest;

public class ConsentService extends SessionAddingService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);
	
	private String consentServiceContext;
	
	public ConsentService(String consentServiceContext) {
		this.consentServiceContext = consentServiceContext;
	}
	
	public boolean addConsentTemplate(AddConsentTemplateRequest request) {
				
		ResponseEntity<String> response = postForEntity(consentServiceContext+"/api/addConsentTemplate", request, String.class);		

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean updateConsentTemplate(UpdateConsentTemplateRequest request) {
		
		ResponseEntity<String> response = postForEntity(consentServiceContext+"/api/updateConsentTemplate", request, String.class);		

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return true;
		} else {
			return false;
		}
		
	}

	
	
}
