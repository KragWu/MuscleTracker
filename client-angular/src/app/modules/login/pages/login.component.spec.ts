import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';
import { of, throwError } from 'rxjs';
import '../../../shared/setup-jasmine-localstorage.spec.ts';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let loginServiceSpy: jasmine.SpyObj<LoginService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    loginServiceSpy = jasmine.createSpyObj('LoginService', ['login', 'token']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: LoginService, useValue: loginServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    component.username = 'testuser';
    component.password = 'testpass';
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login and navigate to /home on success', () => {
    const mockLoginResponse = { headers: new Map([['session', 'session123']]) };
    const mockTokenResponse = { headers: new Map([['token', 'token456']]) };

    loginServiceSpy.login.and.returnValue(of(mockLoginResponse as any));
    loginServiceSpy.token.and.returnValue(of(mockTokenResponse as any));
    const setItemSpy = localStorage.setItem as jasmine.Spy;

    component.login();

    expect(loginServiceSpy.login).toHaveBeenCalledWith('testuser', 'testpass');
    expect(loginServiceSpy.token).toHaveBeenCalledWith('session123');
    expect(setItemSpy).toHaveBeenCalledWith('session', 'session123');
    expect(setItemSpy).toHaveBeenCalledWith('token', 'token456');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should alert error on login failure', () => {
    loginServiceSpy.login.and.returnValue(throwError(() => new Error('fail')));
    spyOn(window, 'alert');
    component.login();
    expect(window.alert).toHaveBeenCalledWith('fail');
  });

  it('should navigate to /register on redirectionRegister', () => {
    component.redirectionRegister();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/register']);
  });
});
