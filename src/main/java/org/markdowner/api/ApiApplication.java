package org.markdowner.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApiApplication {

	public static void main(final String... args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
