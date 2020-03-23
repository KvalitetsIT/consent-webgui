package dk.kvalitetsit.consentservice.configuration;

import dk.kvalitetsit.consentservice.util.LoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConsentServiceWebConfiguration implements WebMvcConfigurer {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsentServiceWebConfiguration.class);

	@Autowired
    private LoggingInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOGGER.debug("Adding interceptors");
		registry.addInterceptor(loggingInterceptor);
	}	

}