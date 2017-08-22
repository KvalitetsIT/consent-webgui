package dk.kvalitetsit.consent.admingui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	private static Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	ConsentService consentService;
			
    @RequestMapping("/")
    public String frontPage(Model model) {    
    	return "index";  
    }
    
}
