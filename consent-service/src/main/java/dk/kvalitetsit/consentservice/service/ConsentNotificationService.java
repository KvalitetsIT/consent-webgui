package dk.kvalitetsit.consentservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dk.kvalitetsit.consentservice.dto.ConsentNotification;
import dk.kvalitetsit.consentservice.dto.ConsentNotification.Function;

public class ConsentNotificationService extends RestTemplate {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsentNotificationService.class);
	
	private String url;

	
	public ConsentNotificationService(String url) {
		this.url = url;
	}
	
	public void sendNotification(String userId, String subject, String mimeType, String document, Function function ) throws ConsentServiceException {
		if (url != null && url.trim().length() > 0) {
			ConsentNotification notification = new ConsentNotification();
			notification.setCitizenId(userId);
			notification.setSubject(subject);
			notification.setDocument(document);
			notification.setDocumentMimeType(mimeType);
			notification.setFunction(function);
			ResponseEntity<String> response = postForEntity(url+"/sendNotification", notification, String.class);
			if (!response.getStatusCode().equals(HttpStatus.OK)) {
				LOGGER.error("Error sending notification - http-status-code:" + response.getStatusCode());
				throw new ConsentServiceException("Error sending notification");
			} 
		} else {
			LOGGER.info("Not sending notification of consent");
		}
	}
}
