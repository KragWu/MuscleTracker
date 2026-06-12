import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from '../../../core/services/keycloak.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss', './../../../app.component.scss'],
    standalone: false
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private keycloak: KeycloakService) {}

  register() {
    // Redirect user to Keycloak self-service registration
    this.keycloak.register();
  }

  redirectionLogin() {
    this.router.navigate(['/login'])
  }

}
