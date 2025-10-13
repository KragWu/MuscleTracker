import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CipherService {

  constructor() { }

  public encrypt(data: string, key: string): string {
    if (key.length === 0) {
      throw new Error("Key cannot be empty");
    }
    let cryptedPassword = ""
    for(let index = 0; index < data.length; index++) {
        let characterEncodeA = data.charCodeAt(index)
        let characterEncodeB = key.charCodeAt(index % key.length)
        let characterEncode = Number(characterEncodeA) + Number(characterEncodeB)
        let characterStrEncode = String.fromCharCode(characterEncode)
        cryptedPassword = cryptedPassword + characterStrEncode
    }
    return cryptedPassword;
  }

  public decrypt(data: string, key: string): string {
    if (key.length === 0) {
      throw new Error("Key cannot be empty");
    }
    let decryptedPassword = ""
    for(let index = 0; index < data.length; index++) {
        let characterDecodeA = data.charCodeAt(index)
        let characterDencodeB = key.charCodeAt(index % key.length)
        let characterDecode = Number(characterDecodeA) - Number(characterDencodeB)
        let characterStrDecode = String.fromCharCode(characterDecode)
        decryptedPassword = decryptedPassword + characterStrDecode
    }
    return decryptedPassword;
  }

  public encryptHex(password: string, secretKey: string): string {
    if (!secretKey) throw new Error('Key cannot be empty');
    const passwordBytes = new TextEncoder().encode(password);
    const keyBytes = new TextEncoder().encode(secretKey);
    const result = new Uint8Array(passwordBytes.length);

    for (let i = 0; i < passwordBytes.length; i++) {
      result[i] = passwordBytes[i] ^ keyBytes[i % keyBytes.length];
    }
    // Encode en base64 pour un header HTTP safe
    return btoa(String.fromCharCode(...result));
  }

  public decryptHex(cryptedPassword: string, secretKey: string): string {
    if (!secretKey) throw new Error('Key cannot be empty');
    const cryptedStr = atob(cryptedPassword);
    const cryptedBytes = Uint8Array.from(cryptedStr, c => c.charCodeAt(0));
    const keyBytes = new TextEncoder().encode(secretKey);
    const result = new Uint8Array(cryptedBytes.length);

    for (let i = 0; i < cryptedBytes.length; i++) {
      result[i] = cryptedBytes[i] ^ keyBytes[i % keyBytes.length];
    }
    return new TextDecoder().decode(result);
  }
}
