package pl.pamsoft.ebs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SimulatedEstimation {
	Integer min;
	Integer max;
	Integer avg;
	Integer count;
	Integer originalEstimation;
	List<Integer> values;
}
