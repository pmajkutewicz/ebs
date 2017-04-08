import {Estimation} from "../../model/Estimation";
import {EstimationService} from "../../services/estimation.service";
import {SimulatedEstimation} from "../../model/SimulatedEstimation";
export abstract class EstimationsEstimateBy {

  private estimations: Estimation[] = [];
  private updateEnabled:boolean = false;
  private simulations: { [key:number]:SimulatedEstimation; } = {};

  constructor(protected estimationService: EstimationService) {
  }

  onDataUpdate(p: Estimation[]) {
    this.estimations = p;
    if (this.estimations.length > 0) {
      this.updateEnabled = true;
    } else {
      this.updateEnabled = false;
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
    this.estimationService.update(this.estimations).subscribe(e => this.estimations = e);
  }

  infoRow(estimation: Estimation){
    if (estimation.actualTime != null) {
      let estimatedTime: number = estimation.estimatedTime.valueOf();
      let actualTime: number = estimation.actualTime.valueOf();
      let minutes = estimatedTime - actualTime;
      if (minutes > 0) {
        return 'Overestimated by: ' + this.minutesToHours(minutes);
      } else if (minutes < 0) {
        return 'Underestimated by: ' + this.minutesToHours(minutes);
      } else {
        return "";
      }
    } else {
      let simulation = this.getSimulation(estimation);
      if (simulation != null) {
        return 'More likely it would be: ' + this.minutesToHours(simulation.avg);
      } else {
        return "";
      }
    }
  }

  simulate(rowId: number, personId: number, estimation: number) {
    this.estimationService.simulate(personId, estimation).subscribe( result => {
        this.simulations[rowId] = result;
        console.log(`Simulating estimation of ${estimation} for ${personId}: ` + JSON.stringify(result));
      }
    );
  }

  getSimulation(estimation: Estimation) {
    let simulation = this.simulations[estimation.id];
    if (simulation == null && estimation.estimatedTime != null) {
      this.simulate(estimation.id, estimation.person.id, estimation.estimatedTime.valueOf());
    }
    return simulation;
  }

}
