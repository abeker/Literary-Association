import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ReaderService {

  private baseUrlReader = environment.baseUrl + "readers";

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public register(body): Observable<any> {
    return this.http.post(this.baseUrlReader, body);
  }

  public isBetaReader(username): Observable<any> {
    return this.http.get(this.baseUrlReader + `/isBetaReader/${username}`);
  }

  public startBetaReaderComment(processId, username): Observable<any> {
      return this.http.get(`${this.baseUrl}welcome/get/${processId}/${username}`);
  }

  public sendBetaReaderComment(body, taskId, processId, username): Observable<any> {
    return this.http.post(`${this.baseUrl}readers/comment/${taskId}/${processId}/${username}`, body);
  }


}
