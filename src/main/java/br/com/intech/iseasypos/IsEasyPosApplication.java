package br.com.intech.iseasypos;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.intech.iseasypos.infrastructure.repository.CustomJpaRepositoryImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class IsEasyPosApplication {	
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(IsEasyPosApplication.class, args);	
	}
	@Bean
	public OpenAPI customOpenAPI(@Value("${application.description}") String description) {
		return new OpenAPI().info(new Info().title(description).version("1.0").termsOfService("http://swagger.io/terms")
				.license(new License().name("Apache 2.0").url("http://spingdoc.org")));
	}
}