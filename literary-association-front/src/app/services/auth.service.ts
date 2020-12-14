import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public login(body): Observable<any> {
    return this.http.put(`${this.baseUrl}auth/login`, body);
  }

  public startRegistrationProcess(): Observable<any> {
    return this.http.get(`${this.baseUrl}welcome/get`);
  }

  public registerUser(user, taskId): Observable<any> {
    return this.http.post(`${this.baseUrl}welcome/post/${taskId}`, user);
  }

}
