import { Component, OnInit } from '@angular/core';
import {EstimationService} from "../../services/estimation.service";
import {PersonStats} from "../../model/PersonStats";
import {ChartData} from "../../model/chart/ChartData";
import {DataPoint} from "../../model/chart/DataPoint";
import {LineChartData} from "../../model/chart/LineChartData";
import {LineDataPoint} from "../../model/chart/LineDataPoint";
import {Estimation} from "../../model/Estimation";

@Component({
  selector: 'app-estimations-stats',
  templateUrl: './estimations-stats.component.html',
  styleUrls: ['./estimations-stats.component.css'],
  providers: [EstimationService]
})
export class EstimationsStatsComponent implements OnInit {

  private personKeys: Array<number> = [];
  private personStats: { [key:number]:PersonStats; } = {};
  private chartData: { [key:number]:ChartData<string>; } = {};
  private scatterPlotChartData: { [key:number]:ChartData<number>; } = {};
  private chartLineData: { [key:number]:LineChartData; } = {};

  constructor(private estimationService: EstimationService) { }

  ngOnInit() {
    this.estimationService.stats().subscribe(res => {
      for (let i = 0; i < res.length; i++) {
        let id = res[i].person.id;
        this.personKeys.push(id);
        this.personStats[id] = res[i];
        this.chartData[id] = this.genHistogramData(id);
        this.scatterPlotChartData[id] = this.getScatterPlotData(id);
        this.chartLineData[id] = this.genLineData(id);
      }
    })
  }

  genLineData(personId: number) : LineChartData {
    let points: LineDataPoint[] = [];
    let stats = this.personStats[personId];
    let index = 0;
    for (let e of stats.estimations) {
      points.push(new LineDataPoint(index, e.estimatedTime.valueOf(), e.actualTime.valueOf()));
      index = index + 1;
    }
    return new LineChartData(points)
  }

  genHistogramData(personId: number) : ChartData<string> {
    let points: DataPoint<string>[] = [];
    let stats = this.personStats[personId];
    for (let key in stats.velocityHistogram) {
      let value = stats.velocityHistogram[key];

      points.push(new DataPoint(key, value))
    }
    return new ChartData(points)
  }

  getScatterPlotData(personId: number) {
    let points: DataPoint<number>[] = [];
    let stats: PersonStats = this.personStats[personId];
    for (let e of stats.estimations) {
      points.push(new DataPoint(e.estimatedTime.valueOf(), e.actualTime.valueOf()))
    }
    return this.calculateRegressionLine(new ChartData(points))
  }

  private calculateRegressionLine(chartData: ChartData<number>): ChartData<number> {
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

  get diagnostic() { return JSON.stringify(this.chartLineData); }
}
