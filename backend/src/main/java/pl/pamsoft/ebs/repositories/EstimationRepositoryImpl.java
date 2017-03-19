package pl.pamsoft.ebs.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.jdbc.core.JdbcTemplate;

import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Task;

public class EstimationRepositoryImpl implements EstimationRepositoryCustom {

	private String simulationQuery =
		"select e from Estimation e " +
		" where e.actualTime is not null " +
		" order by e.estimationTimestamp desc ";
	private final EntityManager em;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public EstimationRepositoryImpl(JpaContext context) {
		this.em = context.getEntityManagerByManagedType(Estimation.class);
	}

	@Override
	public List<Estimation> findAllForSimulation(Person person, int limit) {
		TypedQuery<Estimation> query = em.createQuery(simulationQuery, Estimation.class);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	public List<Estimation> findAllByTaskId(Long taskId) {
		String query =
			"SELECT p.person_id, p.first_name, p.last_name, " +
				" t.task_id, t.name, t.description, " +
				" e.estimation_id, e.actual_time, e.estimated_time, e.estimation_timestamp " +
				"  FROM (person p CROSS JOIN task t) " +
				"  LEFT JOIN estimation e ON t.task_id = e.fk_task_id AND p.person_id = e.fk_person_id " +
				" WHERE t.task_id = ?";

		return jdbcTemplate.query(query, new Object[]{taskId}, (rs, i) -> {
			Person person = new Person();
			person.setId(rs.getLong("person_id"));
			person.setFirstName(rs.getString("first_name"));
			person.setLastName(rs.getString("last_name"));
			Task task = new Task();
			task.setId(rs.getLong("task_id"));
			task.setName(rs.getString("name"));
			task.setDescription(rs.getString("description"));
			Estimation e = new Estimation(person, task, null, null);
			long estimation_id = rs.getLong("estimation_id");
			if (!rs.wasNull()) {
				e.setId(estimation_id);
				e.setActualTime(rs.getInt("actual_time"));
				e.setEstimatedTime(rs.getInt("estimated_time"));
				e.setEstimationTimestamp(rs.getLong("estimation_timestamp"));
			}
			return e;
		});
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
