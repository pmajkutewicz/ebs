package pl.pamsoft.ebs.dto;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Views;

@AllArgsConstructor
@Getter
public class PersonStats {
	@JsonView(Views.PersonStats.class)
	private Person person;
	@JsonView(Views.PersonStats.class)
	private Collection<Estimation> estimations;
	@JsonView(Views.PersonStats.class)
	private Map<String, Long> velocityHistogram;
}
