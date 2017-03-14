import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";

import {Person} from "../model/Person";
import {Observable} from "rxjs/Rx";
import "rxjs/Rx";

@Injectable()
export class PersonService {

  private baseUrl: string = 'http://localhost:8083/person';

  constructor(private http: Http) {

  }

  getAll(): Observable<Person[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}`, options).map((res: Response) => res.json());
  }

  get(id: number): Observable<Person> {
    let person$ = this.http
      .get(`${this.baseUrl}/${id}`, {headers: this.getHeaders()})
      .map(this.mapPerson);
    return person$;
  }

  create(person: Person): Observable<Response> {
    return this.http.post(`${this.baseUrl}`, JSON.stringify(person), {headers: this.getHeaders()});
  }

  save(person: Person): Observable<Response> {
    return this.http
      .put(`${this.baseUrl}/${person.id}`, JSON.stringify(person), {headers: this.getHeaders()});
  }

  deletePerson(id: number): Observable<Response> {
    return this.http.delete(`${this.baseUrl}/${id}`, {headers: this.getHeaders()});
  }

  private getHeaders() {
    let headers = new Headers();
    headers.append('Accept', 'application/json;charset=UTF-8');
    headers.append('Content-Type', 'application/json;charset=UTF-8');
    return headers;
  }

  toPerson(r: any): Person {
    let person = <Person>({
      id: r.id,
      firstName: r.firstName,
      lastName: r.lastName,
    });
    console.log('Parsed person:', person);
    return person;
  }

  mapPerson(response: Response): Person {
    return this.toPerson(response.json());
  }

}
