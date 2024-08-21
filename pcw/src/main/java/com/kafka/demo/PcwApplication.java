package com.kafka.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class PcwApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcwApplication.class, args);
	}

}
