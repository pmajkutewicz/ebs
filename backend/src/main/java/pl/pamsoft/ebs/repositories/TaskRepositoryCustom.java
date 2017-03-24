package pl.pamsoft.ebs.repositories;

import java.util.List;

import pl.pamsoft.ebs.model.Task;

public interface TaskRepositoryCustom {

	List<Task> finAllNonRandomTasks();

}
