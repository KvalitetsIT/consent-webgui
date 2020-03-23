package dk.kvalitetsit.consentservice.repository;

import dk.kvalitetsit.consentservice.entity.Municipality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MunicipalityRepository extends CrudRepository<Municipality,Integer>  {

	List<Municipality> findByName(String name);
}
