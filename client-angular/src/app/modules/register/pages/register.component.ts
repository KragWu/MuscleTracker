import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';
import { switchMap } from 'rxjs';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss', './../../../app.component.scss'],
    standalone: false
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private loginService: LoginService) {}

  register() {
    this.loginService.register(this.username, this.password).pipe(
      switchMap(() => this.loginService.login(this.username, this.password)),
      switchMap((loginResponse) => {
        const session = loginResponse.headers.get('session') || '';
        localStorage.setItem('session', session);
        return this.loginService.token(session);
      })
    ).subscribe({
      next: (tokenResponse) => {
        const token = tokenResponse.headers.get('token') || '';
        localStorage.setItem('token', token);
        this.router.navigate(['/home']); // Redirection après inscription réussie
      },
      error: (error) => {
        alert(error.message);
      }
    });
  }

  redirectionLogin() {
    this.router.navigate(['/login'])
  }

}
