package pl.pamsoft.ebs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "estimation")
public class Estimation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Person person;

	@ManyToOne
	private Task task;

	@NotNull
	private Integer estimatedTime;

	@NotNull
	private Integer actualTime;
}
