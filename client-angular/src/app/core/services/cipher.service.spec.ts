import { CipherService } from './cipher.service';

describe('CipherService', () => {
  let service: CipherService;

  beforeEach(() => {
    service = new CipherService();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should encrypt and decrypt correctly', () => {
    const data = 'password';
    const key = 'key123';
    const encrypted = service.encrypt(data, key);
    const decrypted = service.decrypt(encrypted, key);
    expect(decrypted).toBe(data);
  });

  it('should throw error if key is empty on encrypt', () => {
    expect(() => service.encrypt('data', '')).toThrowError('Key cannot be empty');
  });

  it('should throw error if key is empty on decrypt', () => {
    expect(() => service.decrypt('data', '')).toThrowError('Key cannot be empty');
  });

  it('should return empty string if data is empty', () => {
    const key = 'key123';
    expect(service.encrypt('', key)).toBe('');
    expect(service.decrypt('', key)).toBe('');
  });
});
