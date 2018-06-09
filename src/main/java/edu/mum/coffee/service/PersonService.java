package edu.mum.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.repository.PersonRepository;

@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}
	
	public List<Person> findByEmail(String email) {
		return personRepository.findByEmail(email);
	}

	public Person findById(long id) {
		System.out.println("id :" + id);
		System.out.println("personRepository :" + personRepository);
		Person person = personRepository.findOne(id);
		System.out.println("person :" + person);
		return person;
	}

	public void removePerson(Person person) {
		personRepository.delete(person);
	}

}
