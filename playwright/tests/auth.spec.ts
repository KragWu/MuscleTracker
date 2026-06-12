import { test, expect } from '@playwright/test';
import { loginViaKeycloak } from '../auth-helpers';

const appUrl = 'http://localhost:4200';

test('authenticated user can access home', async ({ page }) => {
  await loginViaKeycloak(page, { username: 'test', password: 'test' });
  await page.goto(`${appUrl}/home`);
  await expect(page).toHaveURL(/.*\/home/);
  await expect(page.locator('h1.title-home')).toHaveText('Muscle Tracker');
});

test('authenticated user can logout and return to login', async ({ page }) => {
  await loginViaKeycloak(page, { username: 'test', password: 'test' });
  await page.goto(`${appUrl}/logout`);
  await page.waitForURL(/.*\/login/);
  await expect(page).toHaveURL(/.*\/login/);
});

test('unauthenticated access to home redirects to Keycloak login', async ({ page }) => {
  await page.goto(`${appUrl}/home`);
  await expect(page).toHaveURL(/.*\/protocol\/openid-connect\/auth.*/);
});
