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

public class TaskRepositoryImpl implements TaskRepositoryCustom{

	private final EntityManager em;
	private String taskQuery = "select t from Task t where t.name != :name";

	@Override
	public List<Task> finAllNonRandomTasks() {
		TypedQuery<Task> query = em.createQuery(taskQuery, Task.class);
		query.setParameter("name", Const.RANDOM_ESTIMATIONS);
		return query.getResultList();
	}

	@Autowired
	public TaskRepositoryImpl(JpaContext context) {
		this.em = context.getEntityManagerByManagedType(Estimation.class);
	}
}
