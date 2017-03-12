package pl.pamsoft.ebs.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.repositories.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Person> listAll() {
		return repository.findAll();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void save(Person person) {
		repository.save(person);
	}
}
