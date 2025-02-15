import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';

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
    this.loginService.login(this.username, this.password).subscribe({
      next: (session) => {
        localStorage.setItem('token', session.token);
        this.router.navigate(['/home']); // Exemple de redirection après connexion réussie
      }, error: (error) => {
        alert(error.message);
      }});
  }

  redirectionRegister() {
    this.router.navigate(['/register'])
  }
}
