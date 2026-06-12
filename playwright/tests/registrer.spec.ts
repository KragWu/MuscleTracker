import { test, expect } from '@playwright/test';

test('Rendering register page', async ({ page }) => {
  await page.goto('http://localhost:4200/register');
  await expect(page).toHaveTitle(/Muscle Tracker/);
  await expect(page.getByRole('textbox', { name: 'Identifiant' })).toBeVisible();
  await expect(page.getByRole('textbox', { name: 'Mot de passe' })).toBeVisible();
  await expect(page.getByRole('button', { name: 'Inscription' })).toBeVisible();
  await expect(page.getByText('Si déjà un compte, cliquez ici')).toBeVisible();
});
