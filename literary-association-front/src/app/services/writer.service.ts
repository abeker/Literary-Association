import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WriterService {
  private baseUrl = environment.baseUrl + "writers";

  constructor(private http: HttpClient) { }

  public publishStart(): Observable<any> {
    return this.http.get(this.baseUrl+"/publish-start");
  }

  public submitPublishForm(map: any): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.post(this.baseUrl+`/submit-form/${processId}`, map);
  }

}

