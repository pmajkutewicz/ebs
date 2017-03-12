package pl.pamsoft.ebs.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pamsoft.ebs.error.BadRequestException;
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

	@RequestMapping(method = RequestMethod.POST)
	public void save(Person person) throws BadRequestException {
		if (null != person.getId()) {
			throw new BadRequestException("Id must be null");
		}
		repository.save(person);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void update(Person person) throws BadRequestException {
		if (null == person.getId()) {
			throw new BadRequestException("Id must not be null");
		}
		repository.save(person);
	}
}
