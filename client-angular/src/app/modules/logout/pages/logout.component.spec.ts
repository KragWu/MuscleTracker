import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LogoutComponent } from './logout.component';
import { Router } from '@angular/router';
import { KeycloakService } from '../../../core/services/keycloak.service';

describe('LogoutComponent', () => {
  let fixture: ComponentFixture<LogoutComponent>;
  let routerSpy: jasmine.SpyObj<Router>;
  let keycloakServiceSpy: jasmine.SpyObj<KeycloakService>;

  beforeEach(async () => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    keycloakServiceSpy = jasmine.createSpyObj('KeycloakService', ['logout']);

    await TestBed.configureTestingModule({
      declarations: [LogoutComponent],
      providers: [
        { provide: Router, useValue: routerSpy },
        { provide: KeycloakService, useValue: keycloakServiceSpy }
      ]
    }).compileComponents();
  });

  it('should call Keycloak logout and navigate to /login', () => {
    fixture = TestBed.createComponent(LogoutComponent);
    fixture.detectChanges();

    expect(keycloakServiceSpy.logout).toHaveBeenCalled();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});
