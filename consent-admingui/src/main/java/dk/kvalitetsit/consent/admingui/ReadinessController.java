package dk.kvalitetsit.consent.admingui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadinessController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ReadinessController.class);
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/info", method = RequestMethod.GET)
	public String getConsentStatus()  {
	    return "OK";
	}
}
