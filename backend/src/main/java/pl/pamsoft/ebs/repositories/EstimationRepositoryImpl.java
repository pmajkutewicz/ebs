package pl.pamsoft.ebs.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Task;

public class EstimationRepositoryImpl implements EstimationRepositoryCustom {

	private final EntityManager em;
	private String simulationQuery =
		"select e from Estimation e " +
			" where e.actualTime is not null " +
			" order by e.estimationTimestamp desc ";
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Estimation> estimationRowMapper = (rs, i) -> {
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
	};

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
		return jdbcTemplate.query(createQuery(QueryType.BY_TASKS), new Object[]{taskId}, estimationRowMapper);
	}

	public List<Estimation> findAllByPersonId(Long personId) {
		return jdbcTemplate.query(createQuery(QueryType.BY_PERSONS), new Object[]{personId}, estimationRowMapper);
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private String createQuery(QueryType queryType) {
		String query =
			"SELECT p.person_id, p.first_name, p.last_name, " +
				" t.task_id, t.name, t.description, " +
				" e.estimation_id, e.actual_time, e.estimated_time, e.estimation_timestamp " +
				"  FROM (person p CROSS JOIN task t) " +
				"  LEFT JOIN estimation e ON t.task_id = e.fk_task_id AND p.person_id = e.fk_person_id ";

		switch (queryType) {
			case BY_TASKS:
				return query + " WHERE t.task_id = ?";
			case BY_PERSONS:
				return query + " WHERE p.person_id = ?";
		}
		return null;
	}

	private enum QueryType {
		BY_TASKS, BY_PERSONS
	}
}
