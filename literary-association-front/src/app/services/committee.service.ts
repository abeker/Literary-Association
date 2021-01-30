import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommitteeService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public startVote(processId, username): Observable<any> {
    return this.http.get(`${this.baseUrl}welcome/get/${processId}/${username}`);
  }

  public downloadFile(filename): Observable<Blob> {
    return this.http.get(`${this.baseUrl}file/download/${filename}`,{responseType: 'blob'});
  }

  public sendVote(body, taskId, processId, username): Observable<any> {
    return this.http.post(`${this.baseUrl}committee/vote/${taskId}/${processId}/${username}`, body);
  }
 


}
