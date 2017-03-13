package pl.pamsoft.ebs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Entity
@Table(name = "person")
@Getter
public class Person extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private transient Boolean generateRandomEntries;

}
