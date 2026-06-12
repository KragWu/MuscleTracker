import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { KeycloakService } from '../core/services/keycloak.service';
import { authGuardGuard } from './auth-guard.guard';

describe('authGuardGuard', () => {
  let keycloakServiceSpy: jasmine.SpyObj<KeycloakService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    keycloakServiceSpy = jasmine.createSpyObj('KeycloakService', ['isLoggedIn']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        { provide: KeycloakService, useValue: keycloakServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });
  });

  function executeGuard() {
    const mockRoute = {} as import('@angular/router').ActivatedRouteSnapshot;
    const mockState = {} as import('@angular/router').RouterStateSnapshot;
    return TestBed.runInInjectionContext(() => authGuardGuard(mockRoute, mockState));
  }

  it('should allow activation when user is logged in', () => {
    keycloakServiceSpy.isLoggedIn.and.returnValue(true);

    const result = executeGuard();

    expect(result).toBeTrue();
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });

  it('should redirect to /login when user is not logged in', () => {
    keycloakServiceSpy.isLoggedIn.and.returnValue(false);

    const result = executeGuard();

    expect(result).toBeFalse();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});
