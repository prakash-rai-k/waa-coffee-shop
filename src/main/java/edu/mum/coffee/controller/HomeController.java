package edu.mum.coffee.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@Controller
public class HomeController {
	@Resource
	private ProductService productService;
	
	@Resource
	private PersonService personService;
	
	@GetMapping({"/", "/home"})
	public String homePage() {
		return "home";
	}

	@GetMapping({"/secure"})
	public String securePage() {
		return "secure";
	}
	
	@RequestMapping("/index")
	public String getProducts(@ModelAttribute("orderline") Orderline orderline, Model model, HttpSession session) {
		System.out.println("Here in Products..-------------->" + productService.getAllProduct());
		model.addAttribute("products", productService.getAllProduct());
		Person person = personService.findById(1);
		session.setAttribute("user", person);
		model.addAttribute("orderLineList", (ArrayList<Orderline>) session.getAttribute("orderLineList"));
		return "index";
	}
}
