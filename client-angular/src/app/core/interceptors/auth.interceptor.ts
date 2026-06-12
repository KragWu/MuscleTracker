import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { KeycloakService } from '../services/keycloak.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private keycloak: KeycloakService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Ensure token is up-to-date (Keycloak refresh if necessary) before sending request
    return from(this.keycloak.updateToken()).pipe(
      switchMap(() => {
        const token = this.keycloak.getToken();
        if (token) {
          const authReq = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
          return next.handle(authReq);
        }
        return next.handle(req);
      })
    );
  }
}
