package pl.pamsoft.ebs.service;

import static java.util.Collections.unmodifiableSet;
import static java.util.EnumSet.of;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.Collections;
import java.util.EnumSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pamsoft.ebs.dto.SimulatedEstimation;
import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.repositories.EstimationRepository;
import pl.pamsoft.ebs.service.simulation.SimulationStatisticCollector;
import pl.pamsoft.ebs.service.simulation.SimulationStatistics;

@Service
public class SimulationServices {

	private EstimationRepository estimationRepository;

	public SimulatedEstimation simulate(Person person, Integer estimate) {
		return simulate(person, estimate, ServiceConst.EXAMPLE_ESTIMATIONS_NB);
	}

	public SimulatedEstimation simulate(Person person, Integer estimate, int limit) {
		List<Estimation> estimations =
			estimationRepository.findAllForSimulation(person, limit);
		SimulationStatistics stats = estimations.stream()
			.map(estimation -> estimation.getVelocity() * estimate)
			.map(Math::round)
			.collect(new SimulationStatisticCollector<>(i -> i));

		return toResult(stats, estimate);
	}

	@Autowired
	public void setEstimationRepository(EstimationRepository estimationRepository) {
		this.estimationRepository = estimationRepository;
	}

	private SimulatedEstimation toResult(SimulationStatistics stats, Integer estimate) {
		int avg = Math.round(Double.valueOf(stats.getAverage()).floatValue());
		int count = Long.valueOf(stats.getCount()).intValue();
		return new SimulatedEstimation(stats.getMin(), stats.getMax(), avg, count, estimate, stats.getValues());
	}

}
