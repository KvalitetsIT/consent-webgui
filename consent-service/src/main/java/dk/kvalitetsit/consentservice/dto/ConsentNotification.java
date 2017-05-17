package dk.kvalitetsit.consentservice.dto;

import java.util.Date;

public class ConsentNotification {

	private String citizenId;
	
	private Date eventTime;
	
	private String subject;
	
	private String documentMimeType;
	
	private String document;

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDocumentMimeType() {
		return documentMimeType;
	}

	public void setDocumentMimeType(String documentMimeType) {
		this.documentMimeType = documentMimeType;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
}
