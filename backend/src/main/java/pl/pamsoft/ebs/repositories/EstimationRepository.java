package pl.pamsoft.ebs.repositories;

import java.util.List;

import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Person;
import pl.pamsoft.ebs.model.Task;

public interface EstimationRepository extends BaseRepository<Estimation>, EstimationRepositoryCustom {

	List<Estimation> findAllByPerson(Person person);

	List<Estimation> findAllByTask(Task task);
}
