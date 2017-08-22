package dk.kvalitetsit.consentservice.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SessionData {
	
	public String SessionId;	
	
	public String SamlToken;
	
	public String Hash;

	public Map<String,List<String>> UserAttributes;
	
	public Map<String,String> SessionAttributes;	

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	public Date Timestamp;
	
	public String getSessionId() {
		return SessionId;
	}

	public Map<String, List<String>> getUserAttributes() {
		return UserAttributes;
	}
	
	public String getUserAttribute(String a) {
		if (UserAttributes == null) {
			return null;
		}
		
		if (UserAttributes.get(a) != null) {
			return UserAttributes.get(a).get(0);
		}
		
		return "";
	}

	public Map<String, String> getSessionAttributes() {
		return SessionAttributes;
	}

	public Date getTimestamp() {
		return Timestamp;
	}
	
	public String getSamlToken() {
		return SamlToken;
	}

	public void setSamlToken(String SamlToken) {
		this.SamlToken = SamlToken;
	}
		
	public String getHash() {
		return Hash;
	}

	public void setHash(String hash) {
		Hash = hash;
	}
}
