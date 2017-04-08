import {Component, OnInit} from "@angular/core";
import {TaskService} from "../../services/task.service";
import {Task} from "../../model/Task";
import {EstimationService} from "../../services/estimation.service";
import {EstimationsEstimateBy} from "./estimations-estimate-by";

@Component({
  selector: 'app-estimations-estimate-by-task',
  templateUrl: './estimations-estimate-by-task.component.html',
  styleUrls: ['./estimations-estimate-by-task.component.css'],
  providers: [TaskService, EstimationService]
})
export class EstimationsEstimateByTaskComponent extends EstimationsEstimateBy implements OnInit {

  private tasks: Task[] = [];

  constructor(private taskService: TaskService, estimationService: EstimationService) {
    super(estimationService)
  }

  ngOnInit() {
    this.taskService.getAll().subscribe(e => this.tasks = e);
  }

  onTaskChange(taskId: number) {
    this.estimationService.getAllByTask(taskId).subscribe(e => this.onDataUpdate(e));
  }
}
