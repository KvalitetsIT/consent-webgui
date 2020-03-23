package dk.kvalitetsit.consentservice.service;

import dk.kvalitetsit.consentservice.entity.Municipality;
import dk.kvalitetsit.consentservice.repository.MunicipalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipalityService {

    private static Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);

    @Autowired
    MunicipalityRepository municipalityRepository;


    public Optional<Municipality> getMunicipality(int id) {
        LOGGER.debug("Lookup municipality for: " + id);
        return municipalityRepository.findById(id);
    }


    public Municipality findByName(String name) {
        LOGGER.debug("Lookup municipality for: " + name);
        List<Municipality> byName = municipalityRepository.findByName(name);
        if( byName.size() == 0) {
            LOGGER.warn("Municipality missing: " + name);
            return null;
        } else if (byName.size() > 1) {
            LOGGER.error("Multiple municipalities found: "+ name);
        }
        return byName.get(0);
    }
}
