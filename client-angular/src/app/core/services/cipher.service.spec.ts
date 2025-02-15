import { TestBed } from '@angular/core/testing';

import { CipherService } from './cipher.service';

describe('CipherService', () => {
  let service: CipherService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CipherService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should encrypt data correctly', () => {
    const data = 'testdata';
    const key = 'key';
    const encryptedData = service.encrypt(data, key);
    expect(encryptedData).not.toEqual(data);
    expect(encryptedData.length).toEqual(data.length);
  });

  it('should decrypt data correctly', () => {
    const data = 'testdata';
    const key = 'key';
    const encryptedData = service.encrypt(data, key);
    const decryptedData = service.decrypt(encryptedData, key);
    expect(decryptedData).toEqual(data);
  });

  it('should throw an error when encrypting with an empty key', () => {
    const data = 'testdata';
    const key = '';
    expect(() => service.encrypt(data, key)).toThrowError('Key cannot be empty');
  });

  it('should throw an error when decrypting with an empty key', () => {
    const data = 'testdata';
    const key = '';
    expect(() => service.decrypt(data, key)).toThrowError('Key cannot be empty');
  });

  it('should handle encryption and decryption with numeric characters', () => {
    const data = '1234567890';
    const key = 'key123';
    const encryptedData = service.encrypt(data, key);
    const decryptedData = service.decrypt(encryptedData, key);
    expect(decryptedData).toEqual(data);
  });

  it('should handle encryption and decryption with mixed case characters', () => {
    const data = 'TestData';
    const key = 'Key';
    const encryptedData = service.encrypt(data, key);
    const decryptedData = service.decrypt(encryptedData, key);
    expect(decryptedData).toEqual(data);
  });

  it('should handle encryption and decryption with long key', () => {
    const data = 'testdata';
    const key = 'averylongkeythatexceedsthedata';
    const encryptedData = service.encrypt(data, key);
    const decryptedData = service.decrypt(encryptedData, key);
    expect(decryptedData).toEqual(data);
  });

  it('should handle encryption and decryption with short key', () => {
    const data = 'testdata';
    const key = 'k';
    const encryptedData = service.encrypt(data, key);
    const decryptedData = service.decrypt(encryptedData, key);
    expect(decryptedData).toEqual(data);
  });

  it('should handle encryption and decryption with special characters in key', () => {
    const data = 'testdata';
    const key = '!@#$%^&*()';
    const encryptedData = service.encrypt(data, key);
    const decryptedData = service.decrypt(encryptedData, key);
    expect(decryptedData).toEqual(data);
  });
});
