package dk.kvalitetsit.consentservice.entity;

import javax.persistence.*;

@Entity(name="ConsentTemplate")
@Table(name="consent_template")
public class ConsentTemplate {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name = "app_id")
	private String appId;
	
	@Column(nullable = false, name = "mime_type")
	private String mimeType;
	
	@Column(nullable = true, name = "friendly_name")
	private String friendlyName;	
	
	@Column(nullable = true, name = "notification_subject")
	private String notificationSubject;		
	
	@Lob
	@Column(nullable = false)
	private String content;

	@Column
	private int version;	
	
	@Column
	private boolean active;

    @ManyToOne
    private Municipality municipality;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }
}
