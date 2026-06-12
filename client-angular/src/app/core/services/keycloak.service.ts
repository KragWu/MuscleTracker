import { Injectable } from '@angular/core';
import Keycloak, { KeycloakInstance } from 'keycloak-js';

@Injectable({ providedIn: 'root' })
export class KeycloakService {
  private keycloak?: KeycloakInstance;
  private storedToken?: string;

  public initKeycloak(): Promise<boolean> {
    return new Promise(async (resolve) => {
      try {
        this.storedToken = localStorage.getItem('kc_token') || undefined;
        this.keycloak = Keycloak({
          url: 'http://localhost:8081',
          realm: 'muscletracker',
          clientId: 'muscletracker-client'
        });

        await this.keycloak.init({ onLoad: 'check-sso', silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html' });
        resolve(true);
      } catch (err) {
        console.error('Keycloak init failed', err);
        resolve(false);
      }
    });
  }

  public login(): void {
    this.keycloak?.login();
  }

  public logout(): void {
    this.keycloak?.logout({ redirectUri: window.location.origin });
    this.clearStoredToken();
  }

  public getToken(): string | undefined {
    return this.keycloak?.token ?? this.storedToken;
  }

  public setStoredToken(token: string): void {
    this.storedToken = token;
    localStorage.setItem('kc_token', token);
  }

  public register(): void {
    this.keycloak?.register();
  }

  public isLoggedIn(): boolean {
    return !!this.keycloak?.token || !!this.storedToken;
  }

  public updateToken(minValidity: number = 30): Promise<boolean> {
    if (this.storedToken) {
      return Promise.resolve(true);
    }
    if (!this.keycloak) return Promise.resolve(false);
    return this.keycloak.updateToken(minValidity).then(() => true).catch(() => false);
  }

  private clearStoredToken() {
    this.storedToken = undefined;
    localStorage.removeItem('kc_token');
  }
}
