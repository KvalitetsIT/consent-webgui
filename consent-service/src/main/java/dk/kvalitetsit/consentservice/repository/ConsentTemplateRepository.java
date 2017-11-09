package dk.kvalitetsit.consentservice.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import dk.kvalitetsit.consentservice.entity.ConsentTemplate;

public interface ConsentTemplateRepository extends Repository<ConsentTemplate,Long> {
	
	ConsentTemplate save(ConsentTemplate entity);
	
	List<ConsentTemplate> findByAppIdAndActive(String appId, boolean active);
	
	List<ConsentTemplate> findByActive(boolean active);
	
	ConsentTemplate findOne(Long id);
}
