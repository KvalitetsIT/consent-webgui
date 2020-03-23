package dk.kvalitetsit.consentservice.entity;

import java.util.Date;

import javax.persistence.*;

@Entity(name="Consent")
@Table(name="consent")
public class Consent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, name= "citizen_id")
	private String citizenId;
	
	@Column
	private boolean answer;
	
	@Column(nullable = false, name= "creation_date")
	private Date creationDate;
	
	@Column(nullable = true, name= "revocation_date")
	private Date revocationDate;	
	
	@ManyToOne
	private ConsentTemplate consentTemplate;

    @ManyToOne
    private Municipality municipality;

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

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }
}
