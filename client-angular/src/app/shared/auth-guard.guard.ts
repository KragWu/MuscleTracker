import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../core/services/login.service';
import { catchError, map, of } from 'rxjs';

export const authGuardGuard: CanActivateFn = () => {
  const loginService = inject(LoginService);
  const router = inject(Router);
  const session = localStorage.getItem('session') || '';
  const token = localStorage.getItem('token') || '';
  return loginService.authorized(session, token).pipe(
    map(response => {
      if (response.status === 200) {
        return true;
      } else {
        router.navigate(['/logout']);
        return false;
      }
    }),
    catchError(() => {
      router.navigate(['/logout']);
      return of(false);
    })
  );
};
