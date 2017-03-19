import {ChartData} from "../../model/chart/ChartData";
import {DataPoint} from "../../model/chart/DataPoint";
import {Estimation} from "../../model/Estimation";
export abstract class EstimationsEstimateBy {

  private estimations: Estimation[] = [];
  private updateEnabled:boolean = false;
  private chartData: ChartData;

  onDataUpdate(p: Estimation[]) {
    this.estimations = p;
    if (this.estimations.length > 0) {
      this.updateEnabled = true;
      this.chartData = this.generateData(p.slice(0))
    } else {
      this.updateEnabled = false;
      this.chartData = null;
    }
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

  updateEstimates(): void {
    console.log("All estimates updated")
  }

  generateData(estimations : Estimation[]): ChartData {
    let filtered = estimations
      .filter(i => i.estimatedTime != null)
      .filter(i => i.actualTime != null)
      .sort((a, b) => {return a.estimatedTime.valueOf()-b.estimatedTime.valueOf();});
    let points : Array<DataPoint> = [];
    for (let i = 0; i < filtered.length; i++) {
      let estimatedTime = filtered[i].estimatedTime;
      let actualTime = filtered[i].actualTime;
      points.push(new DataPoint(estimatedTime.valueOf(), actualTime.valueOf()));
    }
    return this.calculateRegressionLine(new ChartData(points));
  }

  private calculateRegressionLine(chartData: ChartData): ChartData {
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

  get diagnostic() { return JSON.stringify(this.estimations); }

}
