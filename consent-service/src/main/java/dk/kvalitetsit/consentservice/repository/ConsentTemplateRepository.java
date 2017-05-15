package dk.kvalitetsit.consentservice.repository;

import org.springframework.data.repository.Repository;

import dk.kvalitetsit.consentservice.entity.ConsentTemplate;

public interface ConsentTemplateRepository extends Repository<ConsentTemplate,Long> {
	
	ConsentTemplate save(ConsentTemplate entity);
	
	ConsentTemplate findByAppId(String appId);
    

}
