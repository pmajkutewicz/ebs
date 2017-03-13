package pl.pamsoft.ebs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.repositories.PersonRepository;

@Service
public class PersonServices {

	private PersonRepository personRepository;

	public Person getOne(Long id) {
		return personRepository.getOne(id);
	}

	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
}
