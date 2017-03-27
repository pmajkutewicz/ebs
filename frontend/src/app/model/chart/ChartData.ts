import {DataPoint} from "./DataPoint";
export class ChartData <T> {
  constructor(public dataPoints: Array<DataPoint<T>>) {
  }

}
