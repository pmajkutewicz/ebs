package pl.pamsoft.ebs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pamsoft.ebs.model.Estimation;
import pl.pamsoft.ebs.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
