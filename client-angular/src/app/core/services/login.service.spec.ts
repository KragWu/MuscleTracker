import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { LoginService } from './login.service';
import { CipherService } from './cipher.service';
import { RegistrationDTO } from '../models/registrationdto';
import { StatutDTO } from '../models/statutdto';
import { provideHttpClient } from '@angular/common/http';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;
  let cipherSpy: jasmine.SpyObj<CipherService>;

  beforeEach(() => {
    cipherSpy = jasmine.createSpyObj('CipherService', ['encrypt']);
    TestBed.configureTestingModule({
      imports: [],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: CipherService, useValue: cipherSpy }
      ]
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call login and encrypt credentials', () => {
    cipherSpy.encrypt.and.returnValue('crypted');
    service.login('user', 'pass').subscribe(response => {
      expect(response.body).toEqual({ message: 'ok' } as StatutDTO);
    });

    const req = httpMock.expectOne('http://localhost:8080/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ login: 'crypted', password: 'crypted' } as RegistrationDTO);

    req.flush({ message: 'ok' }, { status: 200, statusText: 'OK' });
  });

  it('should call token with session header', () => {
    service.token('session123').subscribe(response => {
      expect(response.body).toEqual({ message: 'ok' } as StatutDTO);
    });

    const req = httpMock.expectOne('http://localhost:8080/token');
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('session')).toBe('session123');
    req.flush({ message: 'ok' }, { status: 200, statusText: 'OK' });
  });

  it('should call register and encrypt credentials', () => {
    cipherSpy.encrypt.and.returnValue('crypted');
    service.register('user', 'pass').subscribe(response => {
      expect(response).toEqual({ message: 'ok' } as StatutDTO);
    });

    const req = httpMock.expectOne('http://localhost:8080/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ login: 'crypted', password: 'crypted' } as RegistrationDTO);
    req.flush({ message: 'ok' });
  });

  it('should call logout with session and token headers', () => {
    service.logout('session123', 'token456').subscribe(response => {
      expect(response).toEqual({ message: 'ok' } as StatutDTO);
    });

    const req = httpMock.expectOne('http://localhost:8080/logout');
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('session')).toBe('session123');
    expect(req.request.headers.get('token')).toBe('token456');
    req.flush({ message: 'ok' });
  });

  it('should call authorized with session and token headers', () => {
    service.authorized('session123', 'token456').subscribe(response => {
      expect(response.body).toEqual({ message: 'ok' } as StatutDTO);
    });

    const req = httpMock.expectOne('http://localhost:8080/authorize');
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('session')).toBe('session123');
    expect(req.request.headers.get('token')).toBe('token456');
    req.flush({ message: 'ok' }, { status: 200, statusText: 'OK' });
  });

  it('should handle 401 error in login', (done) => {
    cipherSpy.encrypt.and.returnValue('crypted');
    service.login('user', 'pass').subscribe({
      error: (err) => {
        expect(err.message).toContain('Invalid credentials');
        done();
      }
    });

    const req = httpMock.expectOne('http://localhost:8080/login');
    req.flush({}, { status: 401, statusText: 'Unauthorized' });
  });

  it('should handle 500 error in login', (done) => {
    cipherSpy.encrypt.and.returnValue('crypted');
    service.login('user', 'pass').subscribe({
      error: (err) => {
        expect(err.message).toBe('Service unavailable, please retry later.');
        done();
      }
    });

    const req = httpMock.expectOne('http://localhost:8080/login');
    req.flush({}, { status: 500, statusText: 'Server Error' });
  });
});
