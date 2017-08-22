package dk.kvalitetsit.consent.webgui;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionAddingInterceptor implements ClientHttpRequestInterceptor {	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SessionAddingInterceptor.class);
	
	private final static String SESSION = "SESSION";
	
	@Value("${correlationid.httpheader.name}")
	private String correlationIdHeaderName;

	
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();        
        String sessionId = getSessionId();        
        headers.add(SESSION, sessionId);
        String correlationid = copyCorrelationIdToRequest(request);
        LOGGER.debug("Adding SESSION to header: " + sessionId+ " copied correlationId: "+correlationid);
        return execution.execute(request, body);
    }
    
    private String copyCorrelationIdToRequest(HttpRequest request) {
    	HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String correlationId = servletRequest.getHeader(correlationIdHeaderName);
    	if (correlationId != null) {
    		request.getHeaders().add(correlationIdHeaderName, correlationId);
    	}
    	return correlationId;
    }
    
    private String getSessionId() {
    	HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	if (servletRequest != null && servletRequest.getCookies() != null) {
    		for (Cookie c : servletRequest.getCookies()) {
    			if (c.getName().equals(SESSION)) {
    				return c.getValue();
    			}
    		}
    	}
        return "";
    }

}
