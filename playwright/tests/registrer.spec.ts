import { test, expect } from '@playwright/test';

test('Login go to register and callback', async ({ page }) => {
  await page.goto('http://localhost:4200/login');
  await page.getByRole('button', { name: 'Inscription' }).click();
  await page.getByText('Si déjà un compte, cliquez ici').click();
});

test('Register failed', async ({ page }) => {
  await page.goto('http://localhost:4200/login');
  await page.getByRole('button', { name: 'Inscription' }).click();
  await page.getByRole('textbox', { name: 'Identifiant' }).click();
  await page.getByRole('textbox', { name: 'Identifiant' }).fill('test');
  await page.getByRole('textbox', { name: 'Mot de passe' }).click();
  await page.getByRole('textbox', { name: 'Mot de passe' }).fill('essai');
  page.once('dialog', dialog => {
    console.log(`Dialog message: ${dialog.message()}`);
    dialog.dismiss().catch(() => {});
  });
});

test('Rendering register page', async ({ page }) => {
    await page.goto('http://localhost:4200/register');
  
    await expect(page).toHaveTitle(/Muscle Tracker/);
    await expect(page.getByRole('textbox', { name: 'Identifiant' })).toBeVisible();
    await expect(page.getByRole('textbox', { name: 'Mot de passe' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Inscription' })).toBeVisible();
    await expect(page.getByText('Si déjà un compte, cliquez ici')).toBeVisible();
});