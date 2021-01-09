import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentConcentratorService {

  private baseUrl = "https://localhost:8443/api/kp/";

  constructor(private http: HttpClient) { }

  public createLU(id, body): Observable<any> {
    return this.http.post(`${this.baseUrl}literary-association/${id}`, body);
  }

  public getPaymentTypes(): Observable<any> {
    return this.http.get(`${this.baseUrl}payment-type`);
  }
}
