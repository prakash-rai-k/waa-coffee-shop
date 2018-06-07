package edu.mum.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeShopApplication {

	public static void main(String[] args) {
		System.out.println("Hello"); 
		SpringApplication.run(CoffeShopApplication.class, args);
	}
}