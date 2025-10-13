import { test, expect } from '@playwright/test';

test('Rendering login page', async ({ page }) => {
    await page.goto('http://localhost:4200/login');
    await expect(page).toHaveTitle(/Muscle Tracker/);
    await expect(page.getByRole('textbox', { name: 'Identifiant' })).toBeVisible();
    await expect(page.getByRole('textbox', { name: 'Mot de passe' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Connexion' })).toBeVisible();
    await expect(page.getByRole('button', { name: 'Inscription' })).toBeVisible();
});

test('Login succeed', async ({ page }) => {
    await page.goto('http://localhost:4200/login');
    await page.getByRole('textbox', { name: 'Identifiant' }).click();
    await page.getByRole('textbox', { name: 'Identifiant' }).fill('test');
    await page.getByRole('textbox', { name: 'Mot de passe' }).click();
    await page.getByRole('textbox', { name: 'Mot de passe' }).fill('essai');
    await page.getByRole('button', { name: 'Connexion' }).click();
});

test('Login and logout succeed', async ({ page }) => {
    await page.goto('http://localhost:4200/login');
    await page.getByRole('textbox', { name: 'Identifiant' }).click();
    await page.getByRole('textbox', { name: 'Identifiant' }).fill('test');
    await page.getByRole('textbox', { name: 'Mot de passe' }).click();
    await page.getByRole('textbox', { name: 'Mot de passe' }).fill('essai');
    await page.getByRole('button', { name: 'Connexion' }).click();
    await page.getByRole('link', { name: 'Logout' }).click();
});

test('Login failed', async ({ page }) => {
    await page.goto('http://localhost:4200/login');
    await page.getByRole('textbox', { name: 'Identifiant' }).click();
    await page.getByRole('textbox', { name: 'Identifiant' }).fill('test');
    await page.getByRole('textbox', { name: 'Mot de passe' }).click();
    await page.getByRole('textbox', { name: 'Mot de passe' }).fill('test');
    page.once('dialog', dialog => {
        console.log(`Dialog message: ${dialog.message()}`);
        dialog.dismiss().catch(() => {});
    });
});
