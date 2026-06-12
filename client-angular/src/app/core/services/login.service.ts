import { Injectable } from '@angular/core';

/**
 * @deprecated This service is legacy and should not be used. Use KeycloakService instead.
 */
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor() {
    console.warn('LoginService is deprecated. Use KeycloakService instead.');
  }

  public login(): never {
    throw new Error('LoginService is deprecated. Use KeycloakService instead.');
  }

  public token(): never {
    throw new Error('LoginService is deprecated. Use KeycloakService instead.');
  }

  public register(): never {
    throw new Error('LoginService is deprecated. Use KeycloakService instead.');
  }

  public logout(): never {
    throw new Error('LoginService is deprecated. Use KeycloakService instead.');
  }

  public authorized(): never {
    throw new Error('LoginService is deprecated. Use KeycloakService instead.');
  }
}
