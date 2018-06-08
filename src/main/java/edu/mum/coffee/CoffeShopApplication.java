package edu.mum.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("edu.mum.coffee.controller")
public class CoffeShopApplication {

	public static void main(String[] args) {
		System.out.println("Hello"); 
		SpringApplication.run(CoffeShopApplication.class, args);
	}
}