package dk.kvalitetsit.consent.admingui;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consentservice.dto.AddConsentTemplateRequest;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateTO;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateTOs;
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
	
	public List<ConsentTemplateTO> getAllActiveConsentTemplates() {
		
		ResponseEntity<ConsentTemplateTOs> response = getForEntity(consentServiceContext+"/api/getAllActiveConsentTemplates", ConsentTemplateTOs.class);		

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			List<ConsentTemplateTO> rv = response.getBody().getList();
			
			Collections.sort(rv, new Comparator<ConsentTemplateTO>() { public int compare(ConsentTemplateTO arg0, ConsentTemplateTO arg1) {	return arg0.getAppId().compareTo(arg1.getAppId());} });
			
			return rv;
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

	
	
}
