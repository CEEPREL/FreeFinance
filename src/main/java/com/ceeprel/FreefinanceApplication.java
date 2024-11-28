package com.ceeprel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class FreefinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreefinanceApplication.class, args);
	}

}
