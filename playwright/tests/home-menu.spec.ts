import { test, expect } from '@playwright/test';

test('Verify header menu behavioral', async ({ page }) => {
  await page.goto('http://localhost:4200/login');
  await expect(page).toHaveTitle(/Muscle Tracker/);
  await page.getByRole('textbox', { name: 'Identifiant' }).click();
  await page.getByRole('textbox', { name: 'Identifiant' }).fill('test');
  await page.getByRole('textbox', { name: 'Mot de passe' }).click();
  await page.getByRole('textbox', { name: 'Mot de passe' }).fill('essai');
  await page.getByRole('button', { name: 'Connexion' }).click();
  await expect(page).toHaveURL(/.*\/home\/prepare$/);
  await expect(page.getByRole('heading', { name: 'prepare works!' })).toBeVisible();
  await page.getByRole('link', { name: 'Prepare Session' }).click();
  await expect(page).toHaveURL(/.*\/home\/prepare$/);
  await expect(page.getByRole('heading', { name: 'prepare works!' })).toBeVisible();
  await page.getByRole('link', { name: 'Follow-up Session' }).click();
  await expect(page).toHaveURL(/.*\/home\/follow$/);
  await expect(page.getByRole('heading', { name: 'follow works!' })).toBeVisible();
  await page.getByRole('link', { name: 'Plan Session' }).click();
  await expect(page).toHaveURL(/.*\/home\/plan$/);
  await expect(page.getByRole('heading', { name: 'plan works!' })).toBeVisible();  await page.getByRole('link', { name: 'Prepare Session' }).click();
});
