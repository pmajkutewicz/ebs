import { Component, OnInit } from '@angular/core';
import {EstimationService} from "../../services/estimation.service";
import {PersonStats} from "../../model/PersonStats";
import {ChartData} from "../../model/chart/ChartData";
import {DataPoint} from "../../model/chart/DataPoint";

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

  constructor(private estimationService: EstimationService) { }

  ngOnInit() {
    this.estimationService.stats().subscribe(res => {
      for (let i = 0; i < res.length; i++) {
        let id = res[i].person.id;
        this.personKeys.push(id);
        this.personStats[id] = res[i];
        this.chartData[id] = this.genHistogramData(id);
      }
    })
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

  get diagnostic() { return JSON.stringify(this.chartData); }
}
