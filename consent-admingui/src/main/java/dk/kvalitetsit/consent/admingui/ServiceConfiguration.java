package dk.kvalitetsit.consent.admingui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class ServiceConfiguration extends WebMvcConfigurerAdapter {

	private static Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);

	@Value("${consentservice.url}")
	private String consentServiceUrl;
	
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
	public SessionAddingInterceptor sessionAddingInterceptor() {
		LOGGER.debug("Adding sessionAddingInterceptor");
		SessionAddingInterceptor sessionAddingInterceptor = new SessionAddingInterceptor();
		return sessionAddingInterceptor;
	}
	
	@Bean
	public ConsentService consentService() {
		ConsentService consentService = new ConsentService(consentServiceUrl);
		return consentService;
	}
	
	@Bean
	public ViewController viewController() {
		ViewController viewController = new ViewController();
		return viewController;
	}
	
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
	 }
	

}
