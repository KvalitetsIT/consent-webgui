package dk.kvalitetsit.consentservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import dk.kvalitetsit.consentservice.util.LoggingInterceptor;

@Configuration
@ComponentScan(value={"dk.kvalitetsit.consentservice"})
public class ConsentServiceWebConfiguration extends WebMvcConfigurerAdapter {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsentServiceWebConfiguration.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOGGER.debug("Adding interceptors");
		registry.addInterceptor(loggingInterceptor());
	}	

	@Bean
	public LoggingInterceptor loggingInterceptor() {
		LOGGER.debug("Creating loggingInterceptor");
		return new LoggingInterceptor();
	}
}