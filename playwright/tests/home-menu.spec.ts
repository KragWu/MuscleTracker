import { test, expect } from '@playwright/test';
import { loginViaKeycloak } from '../auth-helpers';

test('Verify header menu navigation for authenticated user', async ({ page }) => {
  await loginViaKeycloak(page, { username: 'test', password: 'test' });
  await page.goto('http://localhost:4200/home');
  await expect(page).toHaveURL(/.*\/home/);

  await page.getByRole('link', { name: 'Prepare Session' }).click();
  await expect(page).toHaveURL(/.*\/home\/prepare$/);
  await expect(page.getByRole('heading', { name: 'prepare works!' })).toBeVisible();

  await page.getByRole('link', { name: 'Follow-up Session' }).click();
  await expect(page).toHaveURL(/.*\/home\/follow$/);
  await expect(page.getByRole('heading', { name: 'follow works!' })).toBeVisible();

  await page.getByRole('link', { name: 'Plan Session' }).click();
  await expect(page).toHaveURL(/.*\/home\/plan$/);
  await expect(page.getByRole('heading', { name: 'plan works!' })).toBeVisible();
});
