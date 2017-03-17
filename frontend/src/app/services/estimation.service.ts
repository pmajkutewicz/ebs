import {Injectable} from "@angular/core";
import {CONSTANT} from "../app.const";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs";
import {Estimation} from "../model/Estimation";

@Injectable()
export class EstimationService {

  private baseUrl: string = `${CONSTANT.BASE_URL}/estimation`;

  constructor(private http: Http) {
  }

  getAllByTask(taskId: number): Observable<Estimation[]> {
    let options = new RequestOptions({headers: this.getHeaders()});
    return this.http.get(`${this.baseUrl}/byTask/${taskId}`, options).map((res: Response) => res.json());
  }

  private getHeaders() {
    let headers = new Headers();
    headers.append('Accept', 'application/json;charset=UTF-8');
    headers.append('Content-Type', 'application/json;charset=UTF-8');
    return headers;
  }
}
