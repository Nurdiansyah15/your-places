package com.nurd.your.places;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class YourPlacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(YourPlacesApplication.class, args);
	}

}
