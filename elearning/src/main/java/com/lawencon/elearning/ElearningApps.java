package com.lawencon.elearning;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.lawencon")
public class ElearningApps {
	
	public static void main(String[] args) {
		SpringApplication.run(ElearningApps.class, args);
		
	}
}