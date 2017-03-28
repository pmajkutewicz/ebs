import { Component, OnInit } from '@angular/core';
import {EstimationService} from "../../services/estimation.service";
import {PersonStats} from "../../model/PersonStats";
import {ChartData} from "../../model/chart/ChartData";
import {DataPoint} from "../../model/chart/DataPoint";
import {LineChartData} from "../../model/chart/LineChartData";
import {LineDataPoint} from "../../model/chart/LineDataPoint";

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
  private chartLineData: { [key:number]:LineChartData; } = {};

  constructor(private estimationService: EstimationService) { }

  ngOnInit() {
    this.estimationService.stats().subscribe(res => {
      for (let i = 0; i < res.length; i++) {
        let id = res[i].person.id;
        this.personKeys.push(id);
        this.personStats[id] = res[i];
        this.chartData[id] = this.genHistogramData(id);
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

  get diagnostic() { return JSON.stringify(this.chartLineData); }
}
