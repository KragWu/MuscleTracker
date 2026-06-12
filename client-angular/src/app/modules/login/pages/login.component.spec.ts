import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { Router } from '@angular/router';
import { KeycloakService } from '../../../core/services/keycloak.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let keycloakServiceSpy: jasmine.SpyObj<KeycloakService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    keycloakServiceSpy = jasmine.createSpyObj('KeycloakService', ['login']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: KeycloakService, useValue: keycloakServiceSpy },
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

  it('should call Keycloak login on submit', () => {
    component.login();
    expect(keycloakServiceSpy.login).toHaveBeenCalled();
  });

  it('should navigate to /register on redirectionRegister', () => {
    component.redirectionRegister();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/register']);
  });
});
