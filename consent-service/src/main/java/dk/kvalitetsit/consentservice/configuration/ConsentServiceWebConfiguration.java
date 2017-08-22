package dk.kvalitetsit.consentservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import dk.kvalitetsit.consentservice.service.ConsentNotificationService;
import dk.kvalitetsit.consentservice.service.UserService;
import dk.kvalitetsit.consentservice.util.LoggingInterceptor;

@Configuration
@ComponentScan(value={"dk.kvalitetsit.consentservice"})
public class ConsentServiceWebConfiguration extends WebMvcConfigurerAdapter {

	private static Logger LOGGER = LoggerFactory.getLogger(ConsentServiceWebConfiguration.class);

	@Value("${notificationservice.url}")
	private String notificationServiceUrl;
	
	@Value("${userservice.url}")
	private String userServiceUrl;
	
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
	
	@Bean
	public ConsentNotificationService consentNotificationService() {
		ConsentNotificationService consentNotificationService = new ConsentNotificationService(notificationServiceUrl); 
		return consentNotificationService;
	}
	
	@Bean
	public UserService userService() {
		UserService userService = new UserService(userServiceUrl); 
		return userService;
	}
	
}