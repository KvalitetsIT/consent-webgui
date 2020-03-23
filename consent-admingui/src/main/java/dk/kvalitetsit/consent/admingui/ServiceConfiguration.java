package dk.kvalitetsit.consent.admingui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class ServiceConfiguration implements WebMvcConfigurer {

	private static Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);

	@Value("${consentservice.url}")
	private String consentServiceUrl;
	
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
	 }
	

}
