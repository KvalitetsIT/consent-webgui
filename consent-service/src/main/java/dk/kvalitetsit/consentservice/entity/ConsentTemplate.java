package dk.kvalitetsit.consentservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity(name="ConsentTemplate")
@Table(name="consent_template")
public class ConsentTemplate {	
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, name = "app_id")
	private String appId;
	
	@Column(nullable = false, name = "mime_type")
	private String mimeType;
	
	@Column(nullable = true, name = "friendly_name")
	private String friendlyName;	
	
	@Lob
	@Column(nullable = false)
	private String content;

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

}
