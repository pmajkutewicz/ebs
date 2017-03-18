import {Component, OnInit} from "@angular/core";
import {TaskService} from "../../services/task.service";
import {Task} from "../../model/Task";
import {Estimation} from "../../model/Estimation";
import {EstimationService} from "../../services/estimation.service";
import {ChartData} from "../../model/chart/ChartData";
import {DataPoint} from "../../model/chart/DataPoint";

@Component({
  selector: 'app-estimations-estimate-by-task',
  templateUrl: './estimations-estimate-by-task.component.html',
  styleUrls: ['./estimations-estimate-by-task.component.css'],
  providers: [TaskService, EstimationService]
})
export class EstimationsEstimateByTaskComponent implements OnInit {

  private tasks: Task[] = [];
  private estimations: Estimation[] = [];
  private updateEnabled:boolean = false;
  private chartData: ChartData;

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
        this.generateData()
      } else {
        this.updateEnabled = false;
        this.chartData = null;
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
    return `${padHours}h:${padMin}m`;
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

  generateData() {
    var points : Array<DataPoint> = [];
    for (let i = 0; i < (8 + Math.floor(Math.random() * 10)); i++) {
      //this.chartData.push([`Index ${i}`, Math.floor(Math.random() * 100)]);
      points.push(new DataPoint(i, Math.floor(Math.random() * 100)));
    }
    this.chartData = this.calculateRegressionLine(new ChartData(points));
  }

  calculateRegressionLine(chartData: ChartData): ChartData {
    let x_mean = 0;
    let y_mean = 0;
    let term1 = 0;
    let term2 = 0;
    let n_samples = chartData.dataPoints.length;
    for (let i = 0; i < n_samples; i++) {
      x_mean += chartData.dataPoints[i].x;
      y_mean += chartData.dataPoints[i].y;
    }
    // calculate mean
    x_mean /= n_samples;
    y_mean /= n_samples;

    // calculate coefficients
    let xr = 0;
    let yr = 0;
    for (let i = 0; i < n_samples; i++) {
      xr = chartData.dataPoints[i].x - x_mean;
      yr = chartData.dataPoints[i].y - y_mean;
      term1 += xr * yr;
      term2 += xr * xr;
    }

    let b1 = term1 / term2;
    let b0 = y_mean - (b1 * x_mean);

    // fit line using coeffs
    for (let i = 0; i < n_samples; i++) {
      let yhat = b0 + (chartData.dataPoints[i].x * b1);
      chartData.dataPoints[i].yhat = yhat;
    }
    return chartData;
  }
}
