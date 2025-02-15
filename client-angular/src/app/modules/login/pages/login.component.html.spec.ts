import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { LoginComponent } from './login.component';
import { LoginService } from '../../../core/services/login.service';

describe('LoginComponent HTML', () => {
  let loginComponent: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let router: Router;
  let loginService: LoginService;

  beforeEach(async () => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const loginServiceSpy = jasmine.createSpyObj('LoginService', ['login']);

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [FormsModule],
      providers: [
        { provide: Router, useValue: routerSpy },
        { provide: LoginService, useValue: loginServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    loginComponent = fixture.componentInstance;
    router = TestBed.inject(Router);
    loginService = TestBed.inject(LoginService);
    fixture.detectChanges();
  });

  it('should render the form elements correctly', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h1.title').textContent).toContain('Muscle Tracker');
    expect(compiled.querySelector('label[for="username"]').textContent).toContain('Identifiant');
    expect(compiled.querySelector('input#username')).toBeTruthy();
    expect(compiled.querySelector('label[for="password"]').textContent).toContain('Mot de passe');
    expect(compiled.querySelector('input#password')).toBeTruthy();
    expect(compiled.querySelector('button[type="submit"]').textContent).toContain('Connexion');
    expect(compiled.querySelector('button.btn-success').textContent).toContain('Inscription');
  });

  it('should call login method on form submit', () => {
    spyOn(loginComponent, 'login');
    const form = fixture.debugElement.query(By.css('form'));
    form.triggerEventHandler('ngSubmit', null);
    expect(loginComponent.login).toHaveBeenCalled();
  });

  it('should call redirectionRegister method on button click', () => {
    spyOn(loginComponent, 'redirectionRegister');
    const button = fixture.debugElement.query(By.css('button.btn-success'));
    button.triggerEventHandler('click', null);
    expect(loginComponent.redirectionRegister).toHaveBeenCalled();
  });
});