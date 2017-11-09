package dk.kvalitetsit.consentservice.dto;

public class ConsentTemplateTO {
	
	public Long id;
	
	public String appId;
	
	public String friendlyName;	
	
	public String notificationSubject;		

	public int version;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public String getNotificationSubject() {
		return notificationSubject;
	}

	public void setNotificationSubject(String notificationSubject) {
		this.notificationSubject = notificationSubject;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public Long getId() {
		return id;
	}	
	
	
}
