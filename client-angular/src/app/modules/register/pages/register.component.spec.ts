import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { Router } from '@angular/router';
import { KeycloakService } from '../../../core/services/keycloak.service';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let keycloakServiceSpy: jasmine.SpyObj<KeycloakService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    keycloakServiceSpy = jasmine.createSpyObj('KeycloakService', ['register']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: KeycloakService, useValue: keycloakServiceSpy },
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

  it('should call Keycloak register on submit', () => {
    component.register();
    expect(keycloakServiceSpy.register).toHaveBeenCalled();
  });

  it('should navigate to /login on redirectionLogin', () => {
    component.redirectionLogin();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});
