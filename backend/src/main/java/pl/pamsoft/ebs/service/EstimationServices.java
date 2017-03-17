package pl.pamsoft.ebs.service;

import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pamsoft.ebs.dto.SimulatedEstimation;
import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Task;
import pl.pamsoft.ebs.repositories.EstimationRepository;
import pl.pamsoft.ebs.repositories.TaskRepository;

@Service
public class EstimationServices {

	private static final String RANDOM_ESTIMATIONS = "Random_Estimations";
	private static final int HOURS_AS_MINUTES_16 = 16 * 60;
	private static final int EXAMPLE_ESTIMATIONS_NB = 100;
	private ThreadLocalRandom random = ThreadLocalRandom.current();
	private TaskRepository taskRepository;
	private EstimationRepository estimationRepository;

	public List<Estimation> findAllByPerson(Person person) {
		return estimationRepository.findAllByPerson(person);
	}

	public SimulatedEstimation simulate(Person person, Integer estimate) {
		return simulate(person, estimate, EXAMPLE_ESTIMATIONS_NB);
	}

	public SimulatedEstimation simulate(Person person, Integer estimate, int limit) {
		List<Estimation> estimations =
			estimationRepository.findAllForSimulation(person, limit);
		IntSummaryStatistics stats = estimations.stream()
			.map(estimation -> estimation.getVelocity() * estimate)
			.map(Math::round)
			.collect(Collectors.summarizingInt(i -> i));

		return toResult(stats, estimate);
	}

	public void generateRandomEstimates(Person person) {
		Task randomTask = getRandomTask();

		IntStream.rangeClosed(1, EXAMPLE_ESTIMATIONS_NB)
			.mapToObj(i -> createEstimation(person, randomTask))
			.forEach(estimationRepository::save);
	}

	public Collection<Estimation> getEstimationsByTasks(Long taskId) {
		return estimationRepository.findAllByTask(taskRepository.getOne(taskId));
	}

	@Autowired
	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Autowired
	public void setEstimationRepository(EstimationRepository estimationRepository) {
		this.estimationRepository = estimationRepository;
	}

	private SimulatedEstimation toResult(IntSummaryStatistics stats, Integer estimate) {
		int avg = Math.round(Double.valueOf(stats.getAverage()).floatValue());
		int count = Long.valueOf(stats.getCount()).intValue();
		return new SimulatedEstimation(stats.getMin(), stats.getMax(), avg, count, estimate);
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
