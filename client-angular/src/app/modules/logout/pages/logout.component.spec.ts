import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LogoutComponent } from './logout.component';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';
import { of, throwError } from 'rxjs';
import { StatutDTO } from '../../../core/models/statutdto';
import '../../../shared/setup-jasmine-localstorage.spec.ts';

describe('LogoutComponent', () => {
  let fixture: ComponentFixture<LogoutComponent>;
  let routerSpy: jasmine.SpyObj<Router>;
  let loginServiceSpy: jasmine.SpyObj<LoginService>;

  beforeEach(async () => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    loginServiceSpy = jasmine.createSpyObj('LoginService', ['logout']);

    await TestBed.configureTestingModule({
      declarations: [LogoutComponent],
      providers: [
        { provide: Router, useValue: routerSpy },
        { provide: LoginService, useValue: loginServiceSpy }
      ]
    }).compileComponents();
  });

  it('should call logout and clear session on success', () => {
    loginServiceSpy.logout.and.returnValue(of({message: "Logout succeed"} as StatutDTO));
    fixture = TestBed.createComponent(LogoutComponent);
    fixture.detectChanges();

    const removeItemSpy = (localStorage.removeItem as jasmine.Spy);

    expect(loginServiceSpy.logout).toHaveBeenCalledWith('session123', 'token456');
    expect(removeItemSpy).toHaveBeenCalledWith('session');
    expect(removeItemSpy).toHaveBeenCalledWith('token');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should call logout and clear session on error', () => {
    loginServiceSpy.logout.and.returnValue(throwError(() => new Error('fail')));
    fixture = TestBed.createComponent(LogoutComponent);
    fixture.detectChanges();

    const removeItemSpy = (localStorage.removeItem as jasmine.Spy);

    expect(loginServiceSpy.logout).toHaveBeenCalledWith('session123', 'token456');
    expect(removeItemSpy).toHaveBeenCalledWith('session');
    expect(removeItemSpy).toHaveBeenCalledWith('token');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});
