import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';
import { switchMap } from 'rxjs';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss', './../../../app.component.scss'],
    standalone: false
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private loginService: LoginService) {}

  login() {    
    this.loginService.login(this.username, this.password).pipe(
      switchMap((loginResponse) => {
        const session = loginResponse.headers.get('session') || '';
        localStorage.setItem('session', session);
        return this.loginService.token(session);
      })
    ).subscribe({
      next: (tokenResponse) => {
        const token = tokenResponse.headers.get('token') || '';
        localStorage.setItem('token', token);
        this.router.navigate(['/home']); // Redirection après connexion réussie
      },
      error: (error) => {
        alert(error.message);
      }
    });
  }

  redirectionRegister() {
    this.router.navigate(['/register'])
  }
}
