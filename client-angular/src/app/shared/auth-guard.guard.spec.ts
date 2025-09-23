import { TestBed, inject } from '@angular/core/testing';
import { Router } from '@angular/router';
import { LoginService } from '../core/services/login.service';
import { isObservable, of, throwError } from 'rxjs';
import { authGuardGuard } from './auth-guard.guard';
import { HttpResponse } from '@angular/common/http';
import { StatutDTO } from '../core/models/statutdto';

describe('authGuardGuard', () => {
  let loginServiceSpy: jasmine.SpyObj<LoginService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    loginServiceSpy = jasmine.createSpyObj('LoginService', ['authorized']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        { provide: LoginService, useValue: loginServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });
  });

  function executeGuard() {
    // Utilise inject pour exécuter le guard dans le contexte Angular
    const mockRoute = {} as import('@angular/router').ActivatedRouteSnapshot;
    const mockState = {} as import('@angular/router').RouterStateSnapshot;
    const result = TestBed.runInInjectionContext(() => authGuardGuard(mockRoute, mockState));
    return isObservable(result) ? result : of(result);
  }

  it('should allow activation if authorized returns status 200', (done) => {
    loginServiceSpy.authorized.and.returnValue(
      of(new HttpResponse<StatutDTO>({ body: { message: "Authorization succeed" }, status: 200 }))
    );

    executeGuard().subscribe((result) => {
      expect(result).toBeTrue();
      expect(routerSpy.navigate).not.toHaveBeenCalled();
      done();
    });
  });

  it('should redirect to /logout if authorized returns status not 200', (done) => {
    loginServiceSpy.authorized.and.returnValue(
      of(new HttpResponse<StatutDTO>({ body: { message: "Authorization failed" }, status: 401 }))
    );

    executeGuard().subscribe((result) => {
      expect(result).toBeFalse();
      expect(routerSpy.navigate).toHaveBeenCalledWith(['/logout']);
      done();
    });
  });

  it('should redirect to /logout if authorized throws error', (done) => {
    loginServiceSpy.authorized.and.returnValue(throwError(() => new Error('error')));

    executeGuard().subscribe((result) => {
      expect(result).toBeFalse();
      expect(routerSpy.navigate).toHaveBeenCalledWith(['/logout']);
      done();
    });
  });
});
