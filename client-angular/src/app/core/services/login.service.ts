import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { SessionDTO } from '../models/sessiondto';
import { RegistrationDTO } from '../models/registrationdto';
import { CipherService } from './cipher.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private cipherService: CipherService) { }

  public login(username: string, password: string): Observable<SessionDTO> {
    let currentDate = new Date();
    const strCurrentDate = currentDate.toISOString().split('T')[0]
    const cryptedLogin = this.cipherService.encrypt(username, strCurrentDate)
    const cryptedPassword = this.cipherService.encrypt(password, strCurrentDate)

    let registration: RegistrationDTO = {login: cryptedLogin, password: cryptedPassword}
    return this.http.post<SessionDTO>('http://localhost:8080/login', registration).pipe(
      catchError(this.handleError)
    );
  }

  public register(username: string, password: string): Observable<string> {
    let currentDate = new Date();
    const strCurrentDate = currentDate.toISOString().split('T')[0]
    const cryptedLogin = this.cipherService.encrypt(username, strCurrentDate)
    const cryptedPassword = this.cipherService.encrypt(password, strCurrentDate)

    let registration: RegistrationDTO = {login: cryptedLogin, password: cryptedPassword}
    return this.http.post<string>('http://localhost:8080/register', registration).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let message: string = '';
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      message = 'An error occurred:' + error.error.message;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      if (error.status === 400 || error.status === 401) {
        message = 'Invalid credentials';
      }
      if (error.status === 500 || error.status === 501 || error.status === 503 || error.status === 504) {
        message = 'Service unavailable, please retry later.';
      }
      console.error(
        `Backend returned code ${error.status}, body was: `, error.message);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error(message));
  }
}
