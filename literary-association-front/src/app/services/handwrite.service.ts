import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HandwriteService {
  private baseUrl = environment.baseUrl + "handwrites";

  constructor(private http: HttpClient) { }

  public getHandwriteFromProcess(): Observable<any> {
    const processId = localStorage.getItem("publishBookProccessId");
    return this.http.get(this.baseUrl+`/proccess/${processId}`);
  }

}

