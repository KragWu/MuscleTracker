import { Page } from '@playwright/test';

export async function fetchKeycloakToken({
  username,
  password,
  clientId = 'muscletracker-client',
  realm = 'muscletracker',
  keycloakBase = 'http://localhost:8081'
}: { username: string; password: string; clientId?: string; realm?: string; keycloakBase?: string; }) {
  const tokenUrl = `${keycloakBase}/realms/${realm}/protocol/openid-connect/token`;
  const params = new URLSearchParams();
  params.append('grant_type', 'password');
  params.append('client_id', clientId);
  params.append('username', username);
  params.append('password', password);

  const res = await fetch(tokenUrl, {
    method: 'POST',
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    body: params.toString()
  });

  if (!res.ok) throw new Error(`Keycloak token request failed: ${res.status}`);
  return res.json();
}

export async function loginViaKeycloak(page: Page, opts: { username: string; password: string; clientId?: string; realm?: string; keycloakBase?: string }) {
  const data = await fetchKeycloakToken(opts as any);
  const accessToken = data.access_token as string;
  if (!accessToken) throw new Error('No access_token returned');
  // store token in localStorage under kc_token so the Angular app/interceptor picks it up
  await page.addInitScript((token: string) => {
    window.localStorage.setItem('kc_token', token);
  }, accessToken);
}
