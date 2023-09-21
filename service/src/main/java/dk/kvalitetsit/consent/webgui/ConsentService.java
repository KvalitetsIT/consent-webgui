package dk.kvalitetsit.consent.webgui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consent.webgui.dto.ConsentDTOs;
import dk.kvalitetsit.consent.webgui.dto.ConsentTemplateDTO;
import org.springframework.stereotype.Component;

@Component
public class ConsentService extends SessionAddingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);

    @Value("${consentservice.url}")
	private String consentServiceContext;
	
	public ConsentDTOs getAllConsents() {
				
		LOGGER.info("Calling on URL " + consentServiceContext+"/api/getAllConsents");
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

		return response.getStatusCode().equals(HttpStatus.OK);
		
	}
	
	
}
