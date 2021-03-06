package pl.pamsoft.ebs.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import pl.pamsoft.ebs.dto.PersonStats;
import pl.pamsoft.ebs.dto.SimulatedEstimation;
import pl.pamsoft.ebs.error.BadRequestException;
import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Views;
import pl.pamsoft.ebs.service.EstimationServices;
import pl.pamsoft.ebs.service.PersonServices;
import pl.pamsoft.ebs.service.SimulationServices;

@RestController
@RequestMapping("/estimation")
public class EstimationController extends AbstractController<Estimation> {

	private PersonServices personServices;
	private EstimationServices estimationServices;
	private SimulationServices simulationServices;

	@JsonView(Views.EstimationsByTaskOrPerson.class)
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Estimation> update(@RequestBody List<Estimation> estimations) throws BadRequestException {
		estimations.forEach(e -> estimationServices.save(e));
		return estimations;
	}

	@JsonView(Views.PersonEstimations.class)
	@RequestMapping(value = "forPerson/{personId}", method = RequestMethod.GET)
	public List<Estimation> getPersonEstimations(@PathVariable(value = "personId") Long personId)
		throws BadRequestException {
		throwExceptionWhenIdIsNull(personId);

		Person person = personServices.getOne(personId);
		return estimationServices.findAllByPerson(person);
	}

	@RequestMapping(value = "forPerson/{personId}/simulate/{estimation}", method = RequestMethod.GET)
	public SimulatedEstimation getPersonEstimations(@PathVariable(value = "personId") Long personId,
													@PathVariable(value = "estimation") Integer estimation)
		throws BadRequestException {
		throwExceptionWhenIdIsNull(personId);

		Person person = personServices.getOne(personId);
		return simulationServices.simulate(person, estimation);
	}

	@RequestMapping(value = "forPerson/{personId}/simulate/{estimation}/limit/{limit}", method = RequestMethod.GET)
	public SimulatedEstimation getPersonEstimationsWithLimit(@PathVariable(value = "personId") Long personId,
															 @PathVariable(value = "estimation") Integer estimation,
															 @PathVariable(value = "limit") Integer limit)
		throws BadRequestException {
		throwExceptionWhenIdIsNull(personId);

		Person person = personServices.getOne(personId);
		return simulationServices.simulate(person, estimation, limit);
	}

	@JsonView(Views.PersonStats.class)
	@RequestMapping(value = "stats", method = RequestMethod.GET)
	public Collection<PersonStats> stats() throws BadRequestException {
		return estimationServices.getStats();
	}

	@JsonView(Views.EstimationsByTaskOrPerson.class)
	@RequestMapping(value = "byTask/{taskId}",
		method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<Estimation> getEstimationsByTasks(@PathVariable(value = "taskId") Long taskId) {
		return estimationServices.getEstimationsByTasks(taskId);
	}

	@JsonView(Views.EstimationsByTaskOrPerson.class)
	@RequestMapping(value = "byPerson/{personId}",
		method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<Estimation> getEstimationsByPersons(@PathVariable(value = "personId") Long personId) {
		return estimationServices.getEstimationsByPersons(personId);
	}

	@Autowired
	public void setPerson(PersonServices personServices) {
		this.personServices = personServices;
	}

	@Autowired
	public void setEstimationServices(EstimationServices estimationServices) {
		this.estimationServices = estimationServices;
	}

	@Autowired
	public void setSimulationServices(SimulationServices simulationServices) {
		this.simulationServices = simulationServices;
	}
}
