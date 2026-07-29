package com.paymentledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LedgerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgerAppApplication.class, args);
	}
}
