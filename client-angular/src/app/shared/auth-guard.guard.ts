import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from '../core/services/keycloak.service';

export const authGuardGuard: CanActivateFn = () => {
  const keycloakService = inject(KeycloakService);
  const router = inject(Router);

  const isAuthenticated = keycloakService.isLoggedIn();
  if (!isAuthenticated) {
    router.navigate(['/login']);
  }

  return isAuthenticated;
};
