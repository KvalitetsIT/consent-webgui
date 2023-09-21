package dk.kvalitetsit.consent.webgui;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

public class SessionAddingService extends RestTemplate {

	@Autowired
	SessionAddingInterceptor sessionAddingInterceptor;
		
	@PostConstruct
	public void initInterceptors() {
		List<ClientHttpRequestInterceptor> i = new ArrayList<>();
		i.add(sessionAddingInterceptor);
		setInterceptors(i);
	}
}
