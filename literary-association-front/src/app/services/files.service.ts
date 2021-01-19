import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FilesService {


  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public startFileUpload(processId): Observable<any> {
    return this.http.get(`${this.baseUrl}welcome/get/${processId}`);
  }

  public upload(body, taskId): Observable<any> {
    return this.http.post(`${this.baseUrl}file/fileUpload/${taskId}`, body, {responseType: 'text'});
  }

}
