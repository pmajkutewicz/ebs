import {Injectable} from "@angular/core";
import {CONSTANT} from "../app.const";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs";
import {Estimation} from "../model/Estimation";
import {SimulatedEstimation} from "../model/SimulatedEstimation";
import {PersonStats} from "../model/PersonStats";

@Injectable()
export class EstimationService {

  private baseUrl: string = `${CONSTANT.BASE_URL}/estimation`;

  constructor(private http: Http) {
  }

  getAllByTask(taskId: number): Observable<Estimation[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}/byTask/${taskId}`, options).map((res: Response) => res.json());
  }

  getAllByPerson(personId: number): Observable<Estimation[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}/byPerson/${personId}`, options).map((res: Response) => res.json());
  }

  update(estimations: Estimation[]) : Observable<Estimation[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.post(`${this.baseUrl}/update`, JSON.stringify(estimations), options).map((res: Response) => res.json());
  }

  simulate(personId: number, estimation: number) : Observable<SimulatedEstimation> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}/forPerson/${personId}/simulate/${estimation}`, options).map((res: Response) => res.json());
  }

  stats(): Observable<PersonStats[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}/stats`, options).map((res: Response) => res.json());
  }

  private getHeaders() {
    let headers = new Headers();
    headers.append('Accept', 'application/json;charset=UTF-8');
    headers.append('Content-Type', 'application/json;charset=UTF-8');
    return headers;
  }
}
