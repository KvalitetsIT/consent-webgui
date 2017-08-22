package dk.kvalitetsit.consent.webgui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dk.kvalitetsit.consentservice.dto.ConsentDTOs;

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
    	ConsentDTOs consentDTOs = consentService.getAllConsents();
    	if (consentDTOs != null && consentDTOs.list.size() > 0) {
    		model.addAttribute("consents", consentDTOs.getList()); 
    		return "consentList";  
    	} else {
    		model.addAttribute("notification", "Du har ikke afgivet samtykke til nogen applikationer");
    		return "info";  
    	}    	
    }

}
