package dk.kvalitetsit.consentservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Consent")
@Table(name="consent")
public class Consent {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, name= "citizen_id")
	private String citizenId;
	
	@Column
	private boolean answer;
	
	@ManyToOne
	private ConsentTemplate consentTemplate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public ConsentTemplate getConsentTemplate() {
		return consentTemplate;
	}

	public void setConsentTemplate(ConsentTemplate consentTemplate) {
		this.consentTemplate = consentTemplate;
	}
	

}
