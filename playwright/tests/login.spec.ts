import { test, expect } from '@playwright/test';

test('Rendering login page', async ({ page }) => {
  await page.goto('http://localhost:4200/login');
  await expect(page).toHaveTitle(/Muscle Tracker/);
  await expect(page.getByRole('textbox', { name: 'Identifiant' })).toBeVisible();
  await expect(page.getByRole('textbox', { name: 'Mot de passe' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Connexion' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Inscription' })).toBeVisible();
});
