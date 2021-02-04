import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookRequestService {
  private baseUrl = environment.baseUrl + "book-requests";

  constructor(private http: HttpClient) { }

  public getAll(): Observable<any> {
    return this.http.get(this.baseUrl);
  }

  public getBookRequestFromProcess(): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.get(this.baseUrl+`/process/${processId}`);
  }

  
  public canChangeHandwrite(processId): Observable<any> {
    return this.http.get(this.baseUrl+`/change/${processId}`);
  }

  public getFormFields(processId): Observable<any> {
    return this.http.get(environment.baseUrl+`welcome/get/${processId}`);
  }



}

