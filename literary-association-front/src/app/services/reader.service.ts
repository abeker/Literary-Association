import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReaderService {

  private baseUrlReader = environment.baseUrl + "readers";

  constructor(private http: HttpClient) { }

  public register(body): Observable<any> {
    return this.http.post(this.baseUrlReader, body);
  }
}