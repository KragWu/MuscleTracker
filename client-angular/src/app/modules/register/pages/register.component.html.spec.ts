import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { RegisterComponent } from './register.component';
import { LoginService } from '../../../core/services/login.service';

describe('RegisterComponent HTML', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let router: Router;
  let loginService: LoginService;

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
    component = fixture.componentInstance;
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
    expect(compiled.querySelector('button[type="submit"]').textContent).toContain('Inscription');
    expect(compiled.querySelector('a').textContent).toContain('Si déjà un compte, cliquez ici');
  });

  it('should call register method on form submit', () => {
    spyOn(component, 'register');
    const form = fixture.debugElement.query(By.css('form'));
    form.triggerEventHandler('ngSubmit', null);
    expect(component.register).toHaveBeenCalled();
  });

  it('should call redirectionLogin method on link click', () => {
    spyOn(component, 'redirectionLogin');
    const link = fixture.debugElement.query(By.css('a'));
    link.triggerEventHandler('click', null);
    expect(component.redirectionLogin).toHaveBeenCalled();
  });
});