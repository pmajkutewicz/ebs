import {Component, OnInit} from "@angular/core";
import {PersonService} from "../../services/person.service";
import {Person} from "../../model/Person";

@Component({
  selector: 'ebs-persons',
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css'],
  providers: [PersonService]
})
export class PersonsComponent implements OnInit {

  person: Person[] = [];

  model: Person = new Person(null, '', '', false);

  constructor(private personService: PersonService) {
  }

  ngOnInit() {
    this.personService.getAll().subscribe(p => this.person = p)
  }

  onSubmitPerson(): void {
    this.person.push(this.model);
    this.personService.create(this.model).subscribe();
  }

  deletePerson(personId: number): void {
    this.personService.deletePerson(personId).subscribe(result =>
      this.person.splice(this.person.find(i => i.id == personId).id - 1, 1)
    );
  }

}
