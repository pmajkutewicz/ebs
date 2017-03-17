import {Component, OnInit} from "@angular/core";
import {TaskService} from "../../services/task.service";
import {Task} from "../../model/Task";
import {Estimation} from "../../model/Estimation";
import {EstimationService} from "../../services/estimation.service";

@Component({
  selector: 'app-estimations-estimate-by-task',
  templateUrl: './estimations-estimate-by-task.component.html',
  styleUrls: ['./estimations-estimate-by-task.component.css'],
  providers: [TaskService, EstimationService]
})
export class EstimationsEstimateByTaskComponent implements OnInit {

  tasks: Task[] = [];
  estimations: Estimation[] = [];
  updateEnabled:boolean = false;

  constructor(private taskService: TaskService, private estimationService: EstimationService) {
  }

  ngOnInit() {
    this.taskService.getAll().subscribe(p => this.tasks = p);
  }

  onTaskChange(taskId: number) {
    this.estimationService.getAllByTask(taskId).subscribe(p => {
      this.estimations = p;
      if (this.estimations.length > 0) {
        this.updateEnabled = true;
      } else {
        this.updateEnabled = false;
      }
    });
  }

  updateEstimates(): void {
    console.log("All estimates updated")
  }

  minutesToHours(minutes: number) {
    let hours = Math.floor(minutes / 60);
    let min = (minutes - (hours * 60));

    let minStr = '' + min;
    let hourStr = '' + hours;

    let pad = "00";
    let padHours = pad.substring(0, pad.length - hourStr.length) + hourStr;
    let padMin = pad.substring(0, pad.length - minStr.length) + minStr;
    return `${hours}h:${padMin}m`;
  }

  // parseTime(minutes: number): string {
  //   console.log("Parse: " + minutes);
  //   return this.minutesToHours(minutes);
  //   // if (dateString) {
  //   //   return new Date(dateString);
  //   // } else {
  //   //   return null;
  //   // }
  // }

  get diagnostic() { return JSON.stringify(this.estimations); }

}
