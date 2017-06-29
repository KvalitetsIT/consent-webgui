package dk.kvalitetsit.consentservice.dto;

public class ConsentNotification {

	private String citizenId;
	
	private String subject;
	
	private String documentMimeType;
	
	private String document;
	
	private Function function;

	public enum Function {
		AFGIVELSE, ANNULERING
	}
	
	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
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

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
}
