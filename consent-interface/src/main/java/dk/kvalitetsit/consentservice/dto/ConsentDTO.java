package dk.kvalitetsit.consentservice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ConsentDTO {
	
	public long id;
	
	public long templateId;
	
	public String appName;

	public String municipality;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	public Date creationDate;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	public Date revocationDate;
	
	public boolean templateActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getRevocationDate() {
		return revocationDate;
	}

	public void setRevocationDate(Date revocationDate) {
		this.revocationDate = revocationDate;
	}

	public boolean isTemplateActive() {
		return templateActive;
	}

	public void setTemplateActive(boolean templateActive) {
		this.templateActive = templateActive;
	}

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}
