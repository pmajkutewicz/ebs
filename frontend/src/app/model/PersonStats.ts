
import {Person} from "./Person";
import {Task} from "./Task";
import {Estimation} from "./Estimation";
export class PersonStats {
  constructor(public person: Person,
              public estimations: Estimation[],
              public velocityHistogram: { [key:string]:number; }) {
  }

}
