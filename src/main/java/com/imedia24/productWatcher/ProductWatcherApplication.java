package com.imedia24.productWatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class ProductWatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductWatcherApplication.class, args);
	}

}
