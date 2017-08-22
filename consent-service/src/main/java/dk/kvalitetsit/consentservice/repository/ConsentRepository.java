package dk.kvalitetsit.consentservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.Repository;

import dk.kvalitetsit.consentservice.entity.Consent;
import dk.kvalitetsit.consentservice.entity.ConsentTemplate;

public interface ConsentRepository extends Repository<Consent,Long>  {

	Consent save(Consent entity);
	
	Consent findByConsentTemplateAndCitizenIdAndRevocationDate(ConsentTemplate template, String citizenId, Date revocationDate);
	
	List<Consent> findByCitizenId(String citizenId);
	
	Consent findOne(Long id);
}
