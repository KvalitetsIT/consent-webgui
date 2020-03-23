package dk.kvalitetsit.consentservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consentservice.dto.SessionData;
import dk.kvalitetsit.consentservice.util.SessionAddingService;
import org.springframework.stereotype.Component;

@Component
public class UserService extends SessionAddingService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${userservice.url}")
	public String userServiceContext;
	
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
