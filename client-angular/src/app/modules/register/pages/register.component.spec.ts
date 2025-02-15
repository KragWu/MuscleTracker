import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { LoginService } from '../../../core/services/login.service';
import { SessionDTO } from '../../../core/models/sessiondto';
import { FormsModule } from '@angular/forms';

describe('RegisterComponent', () => {
  let registerComponent: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let router: jasmine.SpyObj<Router>;
  let loginService: jasmine.SpyObj<LoginService>;

  beforeEach(async () => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const loginServiceSpy = jasmine.createSpyObj('LoginService', ['register', 'login']);

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [FormsModule],
      providers: [
        { provide: Router, useValue: routerSpy },
        { provide: LoginService, useValue: loginServiceSpy }
      ]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RegisterComponent);
    registerComponent = fixture.componentInstance;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    loginService = TestBed.inject(LoginService) as jasmine.SpyObj<LoginService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(registerComponent).toBeTruthy();
  });

  it('should register and login successfully, then navigate to /home', () => {
    const token = 'test-token';
    const session: SessionDTO = { id: "id", token: token };
    loginService.register.and.returnValue(of(""));
    loginService.login.and.returnValue(of(session));
    spyOn(localStorage, 'setItem');

    registerComponent.username = 'testuser';
    registerComponent.password = 'testpassword';
    registerComponent.register();

    expect(loginService.register).toHaveBeenCalledWith('testuser', 'testpassword');
    expect(loginService.login).toHaveBeenCalledWith('testuser', 'testpassword');
    expect(localStorage.setItem).toHaveBeenCalledWith('token', token);
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should show an alert on register failure', () => {
    const errorMessage = 'Register failed';
    const err = new Error(errorMessage);
    loginService.register.and.returnValue(throwError(() => err));
    spyOn(window, 'alert');

    registerComponent.username = 'testuser';
    registerComponent.password = 'testpassword';
    registerComponent.register();

    expect(loginService.register).toHaveBeenCalledWith('testuser', 'testpassword');
    expect(window.alert).toHaveBeenCalledWith(errorMessage);
  });

  it('should show an alert on login failure after successful register', () => {
    const errorMessage = 'Login failed';
    const err = new Error(errorMessage);
    loginService.register.and.returnValue(of(""));
    loginService.login.and.returnValue(throwError(() => err));
    spyOn(window, 'alert');

    registerComponent.username = 'testuser';
    registerComponent.password = 'testpassword';
    registerComponent.register();

    expect(loginService.register).toHaveBeenCalledWith('testuser', 'testpassword');
    expect(loginService.login).toHaveBeenCalledWith('testuser', 'testpassword');
    expect(window.alert).toHaveBeenCalledWith(errorMessage);
  });

  it('should navigate to /login on redirectionLogin', () => {
    registerComponent.redirectionLogin();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
