package dk.kvalitetsit.consentservice.dto;

public class ConsentStatus {

	public enum Status { ACCEPTED, NOT_ACCEPTED, UNANSWERED, DEPRECATED,NO_MUNICIPALITY };
	
	public String templateContent;
	
	public String templateMimeType;
	
	public String friendlyName;

	public Status status;

	public Integer municipalityId;

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public String getTemplateMimeType() {
		return templateMimeType;
	}

	public void setTemplateMimeType(String templateMimeType) {
		this.templateMimeType = templateMimeType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

    public Integer getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }
}
