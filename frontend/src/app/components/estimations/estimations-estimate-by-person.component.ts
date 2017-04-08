import {Component, OnInit} from "@angular/core";
import {PersonService} from "../../services/person.service";
import {EstimationService} from "../../services/estimation.service";
import {Person} from "../../model/Person";
import {EstimationsEstimateBy} from "./estimations-estimate-by";

@Component({
  selector: 'app-estimations-estimate-by-person',
  templateUrl: './estimations-estimate-by-person.component.html',
  styleUrls: ['./estimations-estimate-by-person.component.css'],
  providers: [PersonService, EstimationService]
})
export class EstimationsEstimateByPersonComponent extends EstimationsEstimateBy implements OnInit {

  private persons: Person[] = [];

  constructor(private personService: PersonService, estimationService: EstimationService) {
    super(estimationService)
  }

  ngOnInit() {
    this.personService.getAll().subscribe(e => this.persons = e);
  }

  onPersonChange(personId: number) {
    this.estimationService.getAllByPerson(personId).subscribe(e => this.onDataUpdate(e));
  }
}
