import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss', './../../../app.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router) {}

  login() {
    if (this.username === 'admin' && this.password === 'password') {
      // Ici, tu peux implémenter la logique de connexion (appel API, etc.)
      alert('Connexion réussie !');
      this.router.navigate(['/home']); // Exemple de redirection après connexion réussie
    } else {
      alert('Pseudo ou mot de passe incorrect');
    }
  }

  redirectionRegister() {
    this.router.navigate(['/register'])
  }
}
