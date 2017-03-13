package pl.pamsoft.ebs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@Configuration
public class CustomWebMvcAutoConfig {
	@Bean
	public Module datatypeHibernateModule() {
		return new Hibernate5Module();
	}

	@Bean
	public Module datatypeJDK8Module() {
		return new Jdk8Module();
	}
}
