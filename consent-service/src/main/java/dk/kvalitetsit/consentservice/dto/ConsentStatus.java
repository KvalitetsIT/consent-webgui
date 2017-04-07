package dk.kvalitetsit.consentservice.dto;

public class ConsentStatus {

	public String consentTemplateId;

	public Boolean consent;

	public String getConsentTemplateId() {
		return consentTemplateId;
	}

	public void setConsentTemplateId(String consentTemplateId) {
		this.consentTemplateId = consentTemplateId;
	}

	public Boolean getConsent() {
		return consent;
	}

	public void setConsent(Boolean consent) {
		this.consent = consent;
	}
}
