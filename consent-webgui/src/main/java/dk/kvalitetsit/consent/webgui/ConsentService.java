package dk.kvalitetsit.consent.webgui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consentservice.dto.ConsentDTOs;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;

public class ConsentService extends SessionAddingService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);
	
	private String consentServiceContext;
	
	public ConsentService(String consentServiceContext) {
		this.consentServiceContext = consentServiceContext;
	}
	
	public ConsentDTOs getAllConsents() {
				
		LOGGER.info("Calling on UTRL " + consentServiceContext+"/api/getAllConsents");
		ResponseEntity<ConsentDTOs> response = getForEntity(consentServiceContext+"/api/getAllConsents", ConsentDTOs.class);		

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			return null;
		}
		
	}
	
	public ConsentTemplateDTO getConsentTemplate(long consentTemplateId) {
		
		ResponseEntity<ConsentTemplateDTO> response = getForEntity(consentServiceContext+"/api/getConsentTemplate?consentTemplateId="+consentTemplateId, ConsentTemplateDTO.class);		

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			return null;
		}
		
	}
	
	public boolean revokeConsent(long consentId) {
		
		ResponseEntity<String> response = getForEntity(consentServiceContext+"/api/revokeConsent?consentId="+consentId, String.class);		

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
}
