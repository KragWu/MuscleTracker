import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CipherService } from './cipher.service';
import { SessionDTO } from '../models/sessiondto';
import { RegistrationDTO } from '../models/registrationdto';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;
  let cipherService: CipherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CipherService]
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
    cipherService = TestBed.inject(CipherService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login successfully', () => {
    const username = 'testuser';
    const password = 'testpassword';
    const token = 'test-token';
    const sessionDTO: SessionDTO = { id: "id", token: token };
    const encryptedUsername = 'encrypted-username';
    const encryptedPassword = 'encrypted-password';

    spyOn(cipherService, 'encrypt').and.callFake((value: string) => {
      if (value === username) return encryptedUsername;
      if (value === password) return encryptedPassword;
      return value;
    });

    service.login(username, password).subscribe((response) => {
      expect(response).toEqual(sessionDTO);
    });

    const req = httpMock.expectOne('http://localhost:8080/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ login: encryptedUsername, password: encryptedPassword });
    req.flush(sessionDTO);
  });

  it('should handle login error', () => {
    const username = 'testuser';
    const password = 'testpassword';
    const encryptedUsername = 'encrypted-username';
    const encryptedPassword = 'encrypted-password';
    const errorMessage = 'Invalid credentials';

    spyOn(cipherService, 'encrypt').and.callFake((value: string) => {
      if (value === username) return encryptedUsername;
      if (value === password) return encryptedPassword;
      return value;
    });

    service.login(username, password).subscribe({
      next: () => fail('expected an error, not a session'),
      error: (error) => expect(error.message).toContain(errorMessage)
    });

    const req = httpMock.expectOne('http://localhost:8080/login');
    req.flush({ message: errorMessage }, { status: 401, statusText: 'Unauthorized' });
  });

  it('should register successfully', () => {
    const username = 'testuser';
    const password = 'testpassword';
    const responseMessage = 'Registration successful';
    const encryptedUsername = 'encrypted-username';
    const encryptedPassword = 'encrypted-password';

    spyOn(cipherService, 'encrypt').and.callFake((value: string) => {
      if (value === username) return encryptedUsername;
      if (value === password) return encryptedPassword;
      return value;
    });

    service.register(username, password).subscribe((response) => {
      expect(response).toEqual(responseMessage);
    });

    const req = httpMock.expectOne('http://localhost:8080/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ login: encryptedUsername, password: encryptedPassword });
    req.flush(responseMessage);
  });

  it('should handle register error', () => {
    const username = 'testuser';
    const password = 'testpassword';
    const encryptedUsername = 'encrypted-username';
    const encryptedPassword = 'encrypted-password';
    const errorMessage = 'Service unavailable, please retry later.';

    spyOn(cipherService, 'encrypt').and.callFake((value: string) => {
      if (value === username) return encryptedUsername;
      if (value === password) return encryptedPassword;
      return value;
    });

    service.register(username, password).subscribe({
      next: () => fail('expected an error, not a success message'),
      error: (error) => expect(error.message).toContain(errorMessage)
    });

    const req = httpMock.expectOne('http://localhost:8080/register');
    req.flush({ message: errorMessage }, { status: 503, statusText: 'Service Unavailable' });
  });
});
