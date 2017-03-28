import {Person} from "./Person";
import {Task} from "./Task";
export class Estimation {
  constructor(public id: number,
              public person: Person,
              public task: Task,
              public estimatedTime: Number,
              public actualTime: Number,
              public estimationTimestamp: Number) {
  }

}
