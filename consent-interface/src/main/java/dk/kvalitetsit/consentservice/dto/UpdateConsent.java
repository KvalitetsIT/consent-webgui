package dk.kvalitetsit.consentservice.dto;

public class UpdateConsent {

	public String userId;
	
	public String appId;
	
	public boolean consent;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public boolean getConsent() {
		return consent;
	}

	public void setConsent(boolean consent) {
		this.consent = consent;
	}
}
