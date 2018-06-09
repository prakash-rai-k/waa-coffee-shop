package edu.mum.coffee.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.coffee.service.ProductService;

@Controller
public class HomeController {
	@Resource
	private ProductService productService;
	
	@GetMapping({"/", "/home"})
	public String homePage() {
		return "home";
	}

	@GetMapping({"/secure"})
	public String securePage() {
		return "secure";
	}
	
	@RequestMapping("/index")
	public String getProducts(Model model) {
		System.out.println("Here in Products..-------------->" + productService.getAllProduct());
		model.addAttribute("products", productService.getAllProduct());
		return "index";
	}
}
