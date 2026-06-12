import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from '../core/services/keycloak.service';

export const keycloakGuard: CanActivateFn = () => {
  const kc = inject(KeycloakService);
  const router = inject(Router);

  if (kc.isLoggedIn()) {
    return true;
  }

  kc.login();
  // Navigation will be handled by Keycloak redirect
  return false;
};
