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
}
