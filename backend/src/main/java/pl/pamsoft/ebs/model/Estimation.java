package pl.pamsoft.ebs.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estimation")
@Getter
@Setter
@NoArgsConstructor
public class Estimation extends AbstractEntity {

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "estimation_id")
	private Long id;

	@JsonView(Views.EstimationsByTask.class)
	@ManyToOne
	@JoinColumn(name = "fk_person_id")
	private Person person;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@ManyToOne
	@JoinColumn(name = "fk_task_id")
	private Task task;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@Column(name = "estimated_time")
	private Integer estimatedTime;

	@JsonView({Views.PersonEstimations.class, Views.EstimationsByTask.class})
	@Column(name = "actual_time")
	private Integer actualTime;

	@JsonView(Views.PersonEstimations.class)
	@Column(name = "estimation_timestamp")
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
