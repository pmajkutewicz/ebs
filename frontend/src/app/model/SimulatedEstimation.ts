
import {Person} from "./Person";
import {Task} from "./Task";
export class SimulatedEstimation {
  constructor(public min: number,
              public max: number,
              public avg: number,
              public count: number,
              public originalEstimation: number,
              public values: number[]) {
  }

}
