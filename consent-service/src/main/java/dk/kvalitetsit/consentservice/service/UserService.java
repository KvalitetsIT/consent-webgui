package dk.kvalitetsit.consentservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consentservice.dto.SessionData;
import dk.kvalitetsit.consentservice.util.SessionAddingService;

public class UserService extends SessionAddingService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public String userServiceContext;
	
	public UserService(String userServiceContext) {
		this.userServiceContext = userServiceContext;
	}
	
	public SessionData getSessionData() {	
		LOGGER.info("Calling " + userServiceContext + "/getsessiondata");
		ResponseEntity<SessionData> response = getForEntity(userServiceContext + "/getsessiondata", SessionData.class); 
		
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			LOGGER.info("Got OK from userservice");
			return response.getBody();
		} else {
			LOGGER.info("Got nothing from userservice");
			return null;
		}
		
	}		
}
