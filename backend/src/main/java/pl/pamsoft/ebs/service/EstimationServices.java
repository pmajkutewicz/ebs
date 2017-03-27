package pl.pamsoft.ebs.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pamsoft.ebs.dto.PersonStats;
import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Task;
import pl.pamsoft.ebs.repositories.EstimationRepository;
import pl.pamsoft.ebs.repositories.PersonRepository;
import pl.pamsoft.ebs.repositories.TaskRepository;

@Service
public class EstimationServices {

	private static final String RANDOM_ESTIMATIONS = "Random_Estimations";
	private static final int HOURS_AS_MINUTES_16 = 16 * 60;
	private static final int EXAMPLE_ESTIMATIONS_NB = 100;
	private ThreadLocalRandom random = ThreadLocalRandom.current();
	private TaskRepository taskRepository;
	private EstimationRepository estimationRepository;
	private PersonRepository personRepository;

	public List<Estimation> findAllByPerson(Person person) {
		return estimationRepository.findAllByPerson(person);
	}

	public void generateRandomEstimates(Person person) {
		Task randomTask = getRandomTask();

		IntStream.rangeClosed(1, EXAMPLE_ESTIMATIONS_NB)
			.mapToObj(i -> createEstimation(person, randomTask))
			.forEach(estimationRepository::save);
	}

	public Collection<Estimation> getEstimationsByTasks(Long taskId) {
		return estimationRepository.findAllByTaskId(taskId);
	}

	public Collection<Estimation> getEstimationsByPersons(Long personId) {
		return estimationRepository.findAllByPersonId(personId);
	}

	public Collection<PersonStats> getStats() {
		Collection<PersonStats> result = new ArrayList<>();
		List<Person> persons = personRepository.findAll();
		for (Person person : persons) {
			List<Estimation> estimations =
				estimationRepository.findAllForSimulation(person, ServiceConst.EXAMPLE_ESTIMATIONS_NB);

			Map<String, Long> temp = estimations.stream().map(Estimation::getVelocity)
				.map(FrequencyBucketService::getLabelForValue)
				.collect(groupingBy(identity(), counting()));

			FrequencyBucketService.getAllBuckets().forEach(i -> temp.putIfAbsent(i, 0L));

			Map<String, Long> velocityHistogram = new LinkedHashMap<>();
			temp.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEach(e -> velocityHistogram.put(e.getKey(), e.getValue()));

			result.add(new PersonStats(person, estimations, velocityHistogram));
		}

		return result;
	}

	public Estimation save(Estimation estimation) {
		return estimationRepository.save(estimation);
	}

	@Autowired
	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Autowired
	public void setEstimationRepository(EstimationRepository estimationRepository) {
		this.estimationRepository = estimationRepository;
	}

	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	private Estimation createEstimation(Person person, Task randomTask) {
		int estimation = random.nextInt(1, HOURS_AS_MINUTES_16 + 1);
		int actual = random.nextInt(1, HOURS_AS_MINUTES_16 + 1);
		return new Estimation(person, randomTask, estimation, actual);
	}

	private Task getRandomTask() {
		Task byName = taskRepository.getByName(RANDOM_ESTIMATIONS);
		if (null == byName) {
			byName = taskRepository.save(new Task(RANDOM_ESTIMATIONS));
		}
		return byName;
	}
}
