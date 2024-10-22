import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss', './../../../app.component.scss']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router) {}

  register() {
    if (this.username === 'admin' && this.password === 'password') {
      // Ici, tu peux implémenter la logique de connexion (appel API, etc.)
      alert('Connexion réussie !');
      this.router.navigate(['/home']); // Exemple de redirection après connexion réussie
    } else {
      alert('Pseudo ou mot de passe incorrect');
    }
  }

  redirectionLogin() {
    this.router.navigate(['/login'])
  }

}
