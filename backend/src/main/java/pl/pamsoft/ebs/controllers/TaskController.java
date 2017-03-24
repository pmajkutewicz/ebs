package pl.pamsoft.ebs.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.pamsoft.ebs.model.Task;
import pl.pamsoft.ebs.repositories.TaskRepository;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController<Task> {

	private TaskRepository taskRepository;

	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<Task> listAll() {
		return taskRepository.finAllNonRandomTasks();
	}

	@Autowired
	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
}
