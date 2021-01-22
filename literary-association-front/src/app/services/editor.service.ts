import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EditorService {
  private baseUrl = environment.baseUrl + "editors";

  constructor(private http: HttpClient) { }

  public processLastOne(): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.get(this.baseUrl + `/get-review-form/${processId}`);
  }

  public sendApprovement(formFields, reason): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.post(this.baseUrl+`/submit-form/${processId}/${reason}`, formFields);
  }

}

