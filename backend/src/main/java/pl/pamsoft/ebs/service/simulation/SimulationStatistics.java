package pl.pamsoft.ebs.service.simulation;

import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

public class SimulationStatistics extends IntSummaryStatistics {

	@Getter
	private List<Integer> values = new LinkedList<>();

	@Override
	public void accept(int value) {
		super.accept(value);
		values.add(value);
	}

	@Override
	public void combine(IntSummaryStatistics other) {
		super.combine(other);
		if (other instanceof SimulationStatistics) {
			SimulationStatistics o = (SimulationStatistics) other;
			values.addAll(o.getValues());
		}
	}

	@Override
	public String toString() {
		return String.format(
			"%s{count=%d, sum=%d, min=%d, average=%f, max=%d, values=%s}",
			this.getClass().getSimpleName(),
			getCount(),
			getSum(),
			getMin(),
			getAverage(),
			getMax(),
			getValues().toString());
	}
}
