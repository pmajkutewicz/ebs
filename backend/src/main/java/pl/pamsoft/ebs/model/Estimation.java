package pl.pamsoft.ebs.model;

import java.util.Date;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "estimation")
@Getter
@NoArgsConstructor
public class Estimation extends AbstractEntity {

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView(Views.EstimationsByTask.class)
	@ManyToOne
	private Person person;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@ManyToOne
	private Task task;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@NotNull
	private Integer estimatedTime;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	private Integer actualTime;

	@JsonView(Views.PersonEstimations.class)
	private Long estimationTimestamp = new Date().getTime();

	public Estimation(Person person, Task task, Integer estimatedTime, Integer actualTime) {
		this.person = person;
		this.task = task;
		this.estimatedTime = estimatedTime;
		this.actualTime = actualTime;
	}

	public Float getVelocity() {
		if (null == actualTime || 0 == actualTime) {
			return null;
		}
		return (float) estimatedTime / actualTime;
	}
}
