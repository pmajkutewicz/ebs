package pl.pamsoft.ebs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;

@Entity
@Table(name = "person")
@Getter
public class Person extends AbstractEntity {

	@JsonView(Views.EstimationsByTask.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView(Views.EstimationsByTask.class)
	@NotNull
	private String firstName;

	@JsonView(Views.EstimationsByTask.class)
	@NotNull
	private String lastName;

	private transient Boolean generateRandomEntries;

}
