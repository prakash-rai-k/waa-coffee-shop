package edu.mum.coffee.controller;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.domain.ProductType;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@Controller
public class ProductController {
	@Resource
	private ProductService productService;
	
	@Resource
	private PersonService personService;

	@RequestMapping("/products")
	public String getProducts(Model model, HttpSession session) {
		//System.out.println("Here in Products..-------------->" + productService.getAllProduct());
		model.addAttribute("products", productService.getAllProduct());
		Person person = personService.findById(1);
		session.setAttribute("user", person);
		return "productList";
	}

	@RequestMapping(value = { "/addProduct" }, method = RequestMethod.GET)
	public String addProduct(@ModelAttribute("product") Product product, Model model) {
		System.out.println("Product Types: -->" + Arrays.asList(ProductType.values()));
		model.addAttribute("productTypes", Arrays.asList(ProductType.values()));
		return "addProduct";
	}

	@RequestMapping(value = { "/addProduct" }, method = RequestMethod.POST)
	public String savePerson(@ModelAttribute("product") @Valid Product product, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			productService.save(product);
			return "redirect:/products";
		}
		model.addAttribute("productTypes", Arrays.asList(ProductType.values()));
		return "addProduct";
	} 
	
	@RequestMapping(value = "/editProduct/{id}", method = RequestMethod.GET)
	public String updateProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute(productService.getProduct(id));
		model.addAttribute("productTypes", Arrays.asList(ProductType.values()));
		return "productDetail";
	}
	
	@RequestMapping(value = { "/editProduct" }, method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult result) {
		System.out.println("Edit Product-->" + product);
		if(!result.hasErrors()) {
			productService.save(product);
			return "redirect:/products";
		}
		return "productDetail";
	} 
	
	@RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("id") int id) {
		productService.delete(productService.getProduct(id));
		return "redirect:/products";
	}
}
