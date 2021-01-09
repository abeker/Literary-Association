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
}
