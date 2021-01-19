import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  user: any = null;
  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    this.user = JSON.parse(localStorage.getItem('user'));
    if(!this.user) {
      return next.handle(request);
    } else {
      const modifiedRequest = request.clone({
        headers: request.headers.append(
          "Auth-Token", this.user.token
        )
      });
      return next.handle(modifiedRequest);
    }
  }
}
