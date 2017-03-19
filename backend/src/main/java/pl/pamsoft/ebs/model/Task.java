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
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
public class Task extends AbstractEntity {

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTaskOrPerson.class})
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id")
	private Long id;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTaskOrPerson.class})
	@NotNull
	private String name;

	private String description;

	public Task(String name) {
		this(name, null);
	}

	public Task(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
