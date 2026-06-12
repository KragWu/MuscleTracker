import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from '../../../core/services/keycloak.service';

@Component({
  selector: 'app-logout',
  standalone: false,
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss', './../../../app.component.scss'],
})
export class LogoutComponent {

  constructor(private router: Router, private keycloak: KeycloakService) {
    // Use Keycloak logout and clear local session keys
    this.keycloak.logout();
    this.clearSession();
    this.router.navigate(['/login']);
  }

  private clearSession() {
    localStorage.removeItem('session');
    localStorage.removeItem('token');
  }

}
