package pl.pamsoft.ebs.repositories;

import java.util.List;

import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;

public interface EstimationRepositoryCustom {

	List<Estimation> findAllForSimulation(Person person, int limit);

	List<Estimation> findAllByTaskId(Long taskId);

	List<Estimation> findAllByPersonId(Long personId);
}
