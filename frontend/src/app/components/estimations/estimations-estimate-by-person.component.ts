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

  constructor(private personService: PersonService, private estimationService: EstimationService) {
    super()
  }

  ngOnInit() {
    this.personService.getAll().subscribe(p => this.persons = p);
  }

  onPersonChange(personId: number) {
    this.estimationService.getAllByPerson(personId).subscribe(p => this.onDataUpdate(p));
  }
}
