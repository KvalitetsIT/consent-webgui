package dk.kvalitetsit.consentservice.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {
	
	@Value("${dataSource.driverClassName}")
	private String driverClassName;
	
	@Value("${dataSource.password}")
	private String password;
	
	@Value("${dataSource.username}")
	private String username;
	
	@Value("${dataSource.url}")
	private String url;
	
	@Bean
	public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
	}
}