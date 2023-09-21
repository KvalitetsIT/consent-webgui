package dk.kvalitetsit.consent.webgui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LogInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

	public static final String MDC_CORRELATION_ID = "correlation-id";
	public static final String MDC_REQUEST_URL = "request-url";

	@Value("${correlationid.httpheader.name}")
	private String correlationIdHeaderName;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String correlationId = request.getHeader(correlationIdHeaderName);
		LOGGER.debug("Extracted header: "+correlationIdHeaderName+" with value:"+correlationId);
		if (correlationId != null) {
			MDC.put(MDC_CORRELATION_ID, correlationId);
		}
		String requestUrl = request.getRequestURL().toString();
		if (requestUrl != null) {
			MDC.put(MDC_REQUEST_URL, requestUrl);
		}
		return true;
	}
}