import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrlUsers = environment.baseUrl + "users";

  constructor(private http: HttpClient) { }

  public getUser(username): Observable<any> {
    username === undefined ? 'notdefined' : username;
    return this.http.get(`${this.baseUrlUsers}/${username}`);
  }

}
