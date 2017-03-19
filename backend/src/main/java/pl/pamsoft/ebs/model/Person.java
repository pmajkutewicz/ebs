package pl.pamsoft.ebs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends AbstractEntity {

	@JsonView(Views.EstimationsByTaskOrPerson.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "person_id")
	private Long id;

	@JsonView(Views.EstimationsByTaskOrPerson.class)
	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@JsonView(Views.EstimationsByTaskOrPerson.class)
	@NotNull
	@Column(name = "last_name")
	private String lastName;

	private transient Boolean generateRandomEntries;

}
