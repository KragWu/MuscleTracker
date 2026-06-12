import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from '../../../core/services/keycloak.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss', './../../../app.component.scss'],
    standalone: false
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private keycloak: KeycloakService) {}

  login() {
    // trigger Keycloak login redirect
    this.keycloak.login();
  }

  redirectionRegister() {
    this.router.navigate(['/register'])
  }
}
