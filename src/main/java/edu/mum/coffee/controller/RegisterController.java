package edu.mum.coffee.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@Controller
public class RegisterController {
	@Resource
	private PersonService personService;
	
	@RequestMapping(value = { "/register" }, method = RequestMethod.GET)
	public String addPerson(@ModelAttribute("person") Person person, Model model) {
		return "register";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			personService.savePerson(person);
			return "redirect:/index";
		}
		return "register";
	} 
}
