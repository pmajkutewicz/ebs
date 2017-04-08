import {Component, OnInit} from "@angular/core";
import {Task} from "../../model/Task";
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css'],
  providers: [TaskService]
})
export class TasksComponent implements OnInit {

  tasks: Task[] = [];

  model: Task = new Task(null, '', '');

  constructor(private taskService: TaskService) {
  }

  ngOnInit() {
    this.taskService.getAll().subscribe(t => this.tasks = t)
  }

  onSubmitTask(): void {
    this.taskService.create(this.model).subscribe(t => this.tasks.push(t));
    this.model = new Task(null, '', '');
  }

  deleteTask(taskId: number): void {
    this.taskService.deletePerson(taskId).subscribe(result =>
      this.tasks.splice(this.tasks.find(i => i.id == taskId).id - 1, 1)
    );
  }

}
