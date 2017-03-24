package pl.pamsoft.ebs.service.simulation;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

public class SimulationStatisticCollector<T> implements Collector<T, SimulationStatistics, SimulationStatistics> {

	private ToIntFunction<? super T> mapper;

	public SimulationStatisticCollector(ToIntFunction<? super T> mapper) {
		this.mapper = mapper;
	}

	@Override
	public Supplier<SimulationStatistics> supplier() {
		return SimulationStatistics::new;
	}

	@Override
	public BiConsumer<SimulationStatistics, T> accumulator() {
		return (r, t) -> r.accept(mapper.applyAsInt(t));
	}

	@Override
	public BinaryOperator<SimulationStatistics> combiner() {
		return (l, r) -> {
			l.combine(r);
			return l;
		};
	}

	@Override
	public Function<SimulationStatistics, SimulationStatistics> finisher() {
		return simulationStatistics -> simulationStatistics;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return EnumSet.of(IDENTITY_FINISH);
	}
}
