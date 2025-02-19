package com.huudan.playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.huudan.playground.${sec}")
@EnableR2dbcRepositories(basePackages = "com.huudan.playground.${sec}")
public class WebFluxPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxPlaygroundApplication.class, args);
	}

}
