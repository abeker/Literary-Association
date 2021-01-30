import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LiteraryAssociationService {
  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public createLA(body): Observable<any> {
    return this.http.post(this.baseUrl + 'la', body);
  }


  public publishBook2Start(): Observable<any> {
    return this.http.get(this.baseUrl+"welcome/startPublish2Process", {responseType: 'text'});
  }

  public getBetaReadersFormField(processId): Observable<any> {
    return this.http.get(`${this.baseUrl}welcome/get/${processId}`);
  }

  public sendChoosenBetaReaders(body, taskId): Observable<any> {
    return this.http.post(`${this.baseUrl}readers/post/${taskId}`, body);
  }


}
