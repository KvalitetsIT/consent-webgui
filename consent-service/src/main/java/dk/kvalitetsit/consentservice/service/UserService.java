package dk.kvalitetsit.consentservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dk.kvalitetsit.consentservice.dto.SessionData;
import dk.kvalitetsit.consentservice.util.SessionAddingService;

public class UserService extends SessionAddingService {

	public String userServiceContext;
	
	public UserService(String userServiceContext) {
		this.userServiceContext = userServiceContext;
	}
	
	public SessionData getSessionData() {				
		ResponseEntity<SessionData> response = getForEntity(userServiceContext + "/getsessiondata", SessionData.class); 
		
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			return null;
		}
		
	}		
}
