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

  public sendCheckPlagiarism(formFields, reason, sendToBetaReaders): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.post(this.baseUrl+`/submit-form-plagiarism/${processId}/${reason}/${sendToBetaReaders}`, formFields);
  }

  public getPlagiatForm(): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.get(this.baseUrl + `/get-plagiat-form/${processId}`);
  }


  public getFormField(): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.get(environment.baseUrl+`welcome/get/${processId}`);
  }


  public submitForm(taskId, formName, formFields): Observable<any> {
    return this.http.post(environment.baseUrl + `welcome/submitForm/${taskId}/${formName}`, formFields);
  }



}

