package com.campax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampaxServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CampaxServerApplication.class, args);
		start();
	}
	
	private static void start() {
		System.out.println("***************************************************************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("****************| Spring Boot App Started |********************");
		System.out.println("*****************-------------------------*********************");
		System.out.println("***************************************************************");
		
	}
	
}