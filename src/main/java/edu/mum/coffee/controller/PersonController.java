package edu.mum.coffee.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@Controller
public class PersonController {
	@Resource
	private PersonService personService;

	@RequestMapping("/persons")
	public String getBooks(Model model) {
		model.addAttribute("persons", personService.getAllPersons());
		return "personList";
	}

	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
	public String addPerson(@ModelAttribute("person") Person person, Model model) {
		return "addPerson";
	}

	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			personService.savePerson(person);
			return "redirect:/persons";
		}
		return "addPerson";
	} 
	
	@RequestMapping(value = "/personDetail/{id}", method = RequestMethod.GET)
	public String updatePerson(@PathVariable("id") Long id, Model model) {
		System.out.println("Person ----> " + personService.findById(id));
		model.addAttribute(personService.findById(id));
		return "personDetail";
	}
	
	@RequestMapping(value = { "/personDetail" }, method = RequestMethod.POST)
	public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult result) {
		System.out.println("Edit Person-->" + person);
		if(!result.hasErrors()) {
			personService.savePerson(person);
			return "redirect:/persons";
		}
		
		return "personDetail";
	} 
	
}
