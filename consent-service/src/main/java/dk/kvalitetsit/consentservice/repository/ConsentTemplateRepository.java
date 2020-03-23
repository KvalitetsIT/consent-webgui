package dk.kvalitetsit.consentservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import dk.kvalitetsit.consentservice.entity.ConsentTemplate;

public interface ConsentTemplateRepository extends CrudRepository<ConsentTemplate,Long> {
	
	List<ConsentTemplate> findByAppIdAndActive(String appId, boolean active);

	List<ConsentTemplate> findByAppIdAndMunicipalityIdAndActive(String appId,int municipalityId, boolean active);

	List<ConsentTemplate> findByActive(boolean active);
	
}
