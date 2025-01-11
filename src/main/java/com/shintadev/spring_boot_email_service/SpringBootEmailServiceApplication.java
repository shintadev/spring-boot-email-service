package com.shintadev.spring_boot_email_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SpringBootEmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailServiceApplication.class, args);
	}

}
