import {Injectable} from "@angular/core";
import {CONSTANT} from "../app.const";
import {Observable} from "rxjs/Rx";

import {Task} from "../model/Task";
import {Headers, Http, RequestOptions, Response} from "@angular/http";

@Injectable()
export class TaskService {

  private baseUrl: string = `${CONSTANT.BASE_URL}/task`;

  constructor(private http: Http) {
  }

  getAll(): Observable<Task[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}`, options).map((res: Response) => res.json());
  }

  get(id: number): Observable<Task> {
    return this.http.get(`${this.baseUrl}/${id}`, {headers: this.getHeaders()}).map(this.toTask);
  }

  create(task: Task): Observable<Task> {
    return this.http.post(`${this.baseUrl}`, JSON.stringify(task), {headers: this.getHeaders()}).map((res: Response) => res.json());
  }

  save(task: Task): Observable<Response> {
    return this.http.put(`${this.baseUrl}/${task.id}`, JSON.stringify(task), {headers: this.getHeaders()});
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

  toTask(r: any): Task {
    return <Task>({
      id: r.id,
      name: r.name,
      description: r.description,
    });
  }

  mapPerson(response: Response): Task {
    return this.toTask(response.json());
  }
}
