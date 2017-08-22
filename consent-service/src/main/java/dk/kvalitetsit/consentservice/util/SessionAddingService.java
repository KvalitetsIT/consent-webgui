package dk.kvalitetsit.consentservice.util;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;


public class SessionAddingService extends RestTemplate {

	@Autowired
	SessionIdAddingIntercepter sessionIdAddingIntercepter;
	
	@PostConstruct
	public void initInterceptors() {
		setInterceptors(Collections.singletonList((ClientHttpRequestInterceptor)sessionIdAddingIntercepter));
	}
}
