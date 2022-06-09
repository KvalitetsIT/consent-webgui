package dk.kvalitetsit.consentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import dk.kvalitetsit.consentservice.dto.AddConsentTemplateRequest;
import dk.kvalitetsit.consentservice.dto.ConsentDTOs;
import dk.kvalitetsit.consentservice.dto.ConsentStatus;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateDTO;
import dk.kvalitetsit.consentservice.dto.ConsentTemplateTOs;
import dk.kvalitetsit.consentservice.dto.SessionData;
import dk.kvalitetsit.consentservice.dto.UpdateConsent;
import dk.kvalitetsit.consentservice.dto.UpdateConsentTemplateRequest;
import dk.kvalitetsit.consentservice.service.ConsentService;
import dk.kvalitetsit.consentservice.service.ConsentServiceException;
import dk.kvalitetsit.consentservice.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ConsentController extends AbstractController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConsentController.class);

	private final ObjectMapper mapper = new ObjectMapper();

	private final String sessionDataKey = "sessionData";
	
	@Autowired
	private ConsentService service;
	
	@Autowired
	private UserService userService;
	
	@Value("${uid.key}")
	private String uidKey;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.GET)
	public ConsentStatus getConsentStatus(@RequestParam("userId") String userId,@RequestParam("municipalityId") Integer municipalityId, @RequestParam("appId") String appId) throws ConsentServiceException {
		return service.getConsentStatus(userId,municipalityId, appId);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/consent", method = RequestMethod.POST)
	public void setConsentStatus(@RequestBody UpdateConsent updateConsent) throws ConsentServiceException {
		service.setConsentStatus(updateConsent.getUserId(),updateConsent.getMunicipalityId(), updateConsent.getAppId(), updateConsent.getConsent());
	}
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/addConsentTemplate", method = RequestMethod.POST)
	public void addConsentTemplate(@RequestBody AddConsentTemplateRequest addConsentTemplateRequest) throws ConsentServiceException {
		service.addConsentTemplate(addConsentTemplateRequest);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/updateConsentTemplate", method = RequestMethod.POST)
	public void updateConsentTemplate(@RequestBody UpdateConsentTemplateRequest updateConsentTemplateRequest) throws ConsentServiceException {
		service.updateConsentTemplate(updateConsentTemplateRequest);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllActiveConsentTemplates", method = RequestMethod.GET)
	public ConsentTemplateTOs getAllActiveConsentTemplates() throws ConsentServiceException {
		return service.getAllActiveConsentTemplates();
	}
	
	
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getAllConsents", method = RequestMethod.GET) 
	public ConsentDTOs getAllConsents(@RequestHeader(sessionDataKey) String sessionData) throws ConsentServiceException, IOException {
		LOGGER.info("Received getAllConsents call");
		String uid = getUid(mapper, uidKey, sessionData);
		LOGGER.info("uid is " + uid);
		return service.getAllConsents(uid);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/revokeConsent", method = RequestMethod.GET) 
	public void revokeConsent(@RequestHeader(sessionDataKey) String sessionData, @RequestParam("consentId") long consentId) throws ConsentServiceException, IOException {
		service.revokeConsent(getUid(mapper, uidKey, sessionData), consentId);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/api/getConsentTemplate", method = RequestMethod.GET)
	public ConsentTemplateDTO getConsentTemplate(@RequestParam("consentTemplateId") long consentTemplateId) throws ConsentServiceException {
		return service.getConsentTemplate(consentTemplateId);
	}

	public static String getUid(ObjectMapper mapper, String uidKey, String sessionData) throws IOException {
		String decodedSessionData = new String(Base64.decodeBase64(sessionData));
		Map<String, Object> map = mapper.readValue(decodedSessionData, Map.class);
		LinkedHashMap<String, ArrayList<String>> userAttributes = (LinkedHashMap<String, ArrayList<String>>) map.get("UserAttributes");
		return userAttributes.get(uidKey).get(0);
	}
}
