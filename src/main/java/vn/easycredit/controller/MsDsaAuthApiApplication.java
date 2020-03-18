package vn.easycredit.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@EnableDiscoveryClient
@ComponentScan(value = "vn.easycredit")
@EnableJpaRepositories(value = "vn.easycredit", repositoryImplementationPostfix = "CustomImpl")
@EntityScan(basePackages = "vn.easycredit")
@EnableAsync
@EnableScheduling
public class MsDsaAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDsaAuthApiApplication.class, args);
	}

}
