package dk.kvalitetsit.consentservice.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dk.kvalitetsit.consentservice.dto.ConsentNotification;

public class ConsentNotificationService extends RestTemplate {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsentNotificationService.class);
	
	private String url;
	
	@Value("${notification.subject}")
	private String notificationSubject;
	
	public ConsentNotificationService(String url) {
		this.url = url;
	}
	
	public void sendNotification(String userId, String appId, String mimeType, String document ) throws ConsentServiceException {
		if (url != null && url.trim().length() > 0) {
			ConsentNotification notification = new ConsentNotification();
			notification.setCitizenId(userId);
			notification.setDocument(document);
			notification.setDocumentMimeType(mimeType);
			notification.setEventTime(new Date());
			notification.setSubject(String.format(notificationSubject, appId));
			
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
