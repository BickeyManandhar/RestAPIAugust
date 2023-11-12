package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringBootRunner.class);

	public static void main(String[] args) {
		
		logger.info("======================================Inside SpringBootRunner: {}",1);
		
		SpringApplication.run(SpringBootRunner.class, args);
		

	}

}
