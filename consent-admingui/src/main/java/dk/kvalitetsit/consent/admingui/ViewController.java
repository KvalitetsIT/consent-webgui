package dk.kvalitetsit.consent.admingui;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateTO;
import dk.kvalitetsit.consentservice.dto.UpdateConsentTemplateRequest;

@Controller
public class ViewController {

	private static Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	ConsentService consentService;
			
    @RequestMapping("/")
    public String frontPage(Model model) {  
    	List<ConsentTemplateTO> tos = consentService.getAllActiveConsentTemplates();
    	List<String> municipalities = tos.stream().map(to -> to.getMunicipality()).distinct().collect(Collectors.toList());
    	model.addAttribute("consentTemplates", tos);
    	model.addAttribute("municipalities", municipalities);
    	model.addAttribute("request",new UploadFileRequest());
    	return "index";  
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
    
    @RequestMapping(value = "/updateConsentTemplate", method = RequestMethod.POST)
    public String updateConsentTemplate(Model model, @ModelAttribute("request") UploadFileRequest request) {
    	try {
    		byte[] file = request.getPdfFile().getBytes();    		
    		UpdateConsentTemplateRequest req = new UpdateConsentTemplateRequest();
    		req.setContent(Base64Utils.encodeToString(file));
    		req.setMimeType("application/pdf");
    		req.setAppId(request.getAppId());
    		req.setMunicipality(request.getMunicipality());
    		consentService.updateConsentTemplate(req);
    		model.addAttribute("notification", "Samtykket er nu opdateret");
    	} catch (Exception e) {
    		LOGGER.error("Template upload failed",e);
    		model.addAttribute("notification", "Fejl ved upload af nyt samtykke");
    	}
    	List<ConsentTemplateTO> tos = consentService.getAllActiveConsentTemplates();
    	model.addAttribute("consentTemplates", tos);
        List<String> municipalities = tos.stream().map(to -> to.getMunicipality()).distinct().collect(Collectors.toList());
        model.addAttribute("municipalities", municipalities);
    	model.addAttribute("request",new UploadFileRequest());
    	return "index";   	
    }
    
}
