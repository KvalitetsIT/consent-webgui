package dk.kvalitetsit.consent.webgui;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dk.kvalitetsit.consentservice.dto.ConsentDTO;
import dk.kvalitetsit.consentservice.dto.ConsentDTOs;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;

@Controller
public class ViewController {

	private static Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	ConsentService consentService;
			
    @RequestMapping("/")
    public String frontPage(Model model) {    
    	return showConsents(model);  
    }
    
	
    @RequestMapping("/showConsents")
    public String showConsents(Model model) {
    	
    	List<ConsentDTO> toShow = new ArrayList<>();    	
    	ConsentDTOs consentDTOs = consentService.getAllConsents();
    	
    	if (consentDTOs != null && consentDTOs.list.size() > 0) {    		
    		for (ConsentDTO c : consentDTOs.getList()) {
    			if (c.getRevocationDate() == null && c.isTemplateActive()) {
    				toShow.add(c);
    			}
    		} 
    	}
    	
    	if (toShow.size() > 0) {
    		model.addAttribute("consents", toShow); 
    		return "consentList";  
    	} else {
    		model.addAttribute("notification", "Du har ikke afgivet samtykke til nogen applikationer");
    		return "info";  
    	}    	
    }
    
    @RequestMapping("/revokeConsent")
    public String revokeConsent(Model model, @RequestParam("consentId") Long consentId) {    
    	consentService.revokeConsent(consentId);
    	return showConsents(model);
    }
    
    @RequestMapping("/showConsentTemplate")
    public ResponseEntity<byte[]>  showConsentTemplate(Model model, @RequestParam("templateId") Long templateId) {    
    	ConsentTemplateDTO template = consentService.getConsentTemplate(templateId);    	
    	byte[] contents = Base64.decodeBase64(template.getContent());
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(template.getMimeType()));
        String filename = "consent.pdf";
        headers.setContentDispositionFormData(filename, filename);
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }
    
    

}
