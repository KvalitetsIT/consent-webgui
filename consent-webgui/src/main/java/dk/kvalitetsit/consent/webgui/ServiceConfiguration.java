package dk.kvalitetsit.consent.webgui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
    private LoggingInterceptor loggingInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LOGGER.debug("Adding interceptors");
		registry.addInterceptor(loggingInterceptor);
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }
	
}
