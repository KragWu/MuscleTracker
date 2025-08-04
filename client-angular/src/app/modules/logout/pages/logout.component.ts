import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';

@Component({
  selector: 'app-logout',
  standalone: false,
  templateUrl: './logout.component.html',
  styleUrls: ['./login.component.scss', './../../../app.component.scss'],
})
export class LogoutComponent {

  constructor(private router: Router, private loginService: LoginService) {
    // Logique de déconnexion si nécessaire
    const session = localStorage.getItem('session') || '';
    const token = localStorage.getItem('token') || '';

    loginService.logout(session, token).subscribe({
      next: () => {
        this.clearSession();
        this.router.navigate(['/login']); // Redirection après déconnexion réussie
      },
      error: () => {
        this.clearSession();
        this.router.navigate(['/login']);
      }
    });
  }

  private clearSession() {
    localStorage.removeItem('session');
    localStorage.removeItem('token');
  }

}
