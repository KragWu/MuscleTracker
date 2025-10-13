import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login.service';
import { of, throwError } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import '../../../shared/setup-jasmine-localstorage.spec.ts';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let loginServiceSpy: jasmine.SpyObj<LoginService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    loginServiceSpy = jasmine.createSpyObj('LoginService', ['register', 'login', 'token']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: LoginService, useValue: loginServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    component.username = 'testuser';
    component.password = 'testpass';
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register and navigate to /home on success', () => {
    const mockLoginResponse = { headers: new HttpHeaders({session: 'session123'}) };
    const mockTokenResponse = { headers: new HttpHeaders({token: 'token456'}) };

    loginServiceSpy.register.and.returnValue(of({message: "Registration successful"}));
    loginServiceSpy.login.and.returnValue(of(mockLoginResponse as any));
    loginServiceSpy.token.and.returnValue(of(mockTokenResponse as any));

    const setItemSpy = (localStorage.setItem as jasmine.Spy);

    component.register();

    expect(loginServiceSpy.register).toHaveBeenCalledWith('testuser', 'testpass');
    expect(loginServiceSpy.login).toHaveBeenCalledWith('testuser', 'testpass');
    expect(loginServiceSpy.token).toHaveBeenCalledWith('session123');
    expect(setItemSpy).toHaveBeenCalledWith('session', 'session123');
    expect(setItemSpy).toHaveBeenCalledWith('token', 'token456');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should alert error on register failure', () => {
    loginServiceSpy.register.and.returnValue(throwError(() => new Error('fail')));
    spyOn(window, 'alert');
    component.register();
    expect(window.alert).toHaveBeenCalledWith('fail');
  });

  it('should navigate to /login on redirectionLogin', () => {
    component.redirectionLogin();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});
