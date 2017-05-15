package dk.kvalitetsit.consentservice.repository;

import org.springframework.data.repository.Repository;

import dk.kvalitetsit.consentservice.entity.Consent;
import dk.kvalitetsit.consentservice.entity.ConsentTemplate;

public interface ConsentRepository extends Repository<Consent,Long>  {

	Consent save(Consent entity);
	
	Consent findByConsentTemplateAndCitizenId(ConsentTemplate template, String citizenId);
	
}
