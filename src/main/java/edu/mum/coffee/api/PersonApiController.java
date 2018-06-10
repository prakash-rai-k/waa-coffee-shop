package edu.mum.coffee.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonApiController {
	@Resource
	private PersonService personService;

	// -------------------Retrieve All
	// Person---------------------------------------------

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getAllPersons() {
		List<Person> persons = personService.getAllPersons();
		if (persons.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Person------------------------------------------

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPerson(@PathVariable("id") Long id) {
		Person person = personService.findById(id);
		if (person == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	// -------------------Create a
	// Product-------------------------------------------

	@CrossOrigin
	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public ResponseEntity<?> createPerson(@RequestBody Person person, UriComponentsBuilder ucBuilder) {
		personService.savePerson(person);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/person/{id}").buildAndExpand(person.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Product
	// ------------------------------------------------

	@RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {

		Person currentPerson = personService.findById(id);

		if (currentPerson == null) {
			return new ResponseEntity("Unable to upate. Person with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		
		currentPerson.setFirstName(person.getFirstName());
		currentPerson.setLastName(person.getLastName());
		currentPerson.setAddress(person.getAddress());
		currentPerson.setEmail(person.getEmail());
		currentPerson.setPhone(person.getPhone());
		currentPerson.setEnable(person.isEnable());
		personService.savePerson(currentPerson);
		return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
	}

	
}
