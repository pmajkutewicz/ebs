package pl.pamsoft.ebs.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;

import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;

public class EstimationRepositoryImpl implements EstimationRepositoryCustom {

	private String simulationQuery =
		"select e from Estimation e " +
		" where e.actualTime is not null " +
		" order by e.estimationTimestamp desc ";
	private final EntityManager em;

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
}
