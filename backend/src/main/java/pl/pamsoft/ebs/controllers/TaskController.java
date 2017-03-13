package pl.pamsoft.ebs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.pamsoft.ebs.model.Task;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController<Task> {

}
