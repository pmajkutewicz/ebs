package pl.pamsoft.ebs.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pamsoft.ebs.error.BadRequestException;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.service.EstimationServices;

@RestController
@RequestMapping("/person")
public class PersonController extends AbstractController<Person> {

	private EstimationServices estimationServices;

	@Autowired
	public void setEstimationServices(EstimationServices estimationServices) {
		this.estimationServices = estimationServices;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void save(@Valid @RequestBody Person person) throws BadRequestException {
		if (null != person.getId()) {
			throw new BadRequestException("Id must be null");
		}
		repository.save(person);
		if (person.getGenerateRandomEntries()) {
			estimationServices.generateRandomEstimates(person);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void update(@Valid @RequestBody Person person) throws BadRequestException {
		throwExceptionWhenIdIsNull(person);

		repository.save(person);
	}

}
