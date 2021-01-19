import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankService {
  private baseUrl = "https://localhost:8443/api/bank";

  constructor(private http: HttpClient) { }

  public getBanks(): Observable<any> {
    return this.http.get(this.baseUrl);
  }
}
