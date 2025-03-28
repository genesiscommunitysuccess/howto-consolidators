import { expect } from '@genesislcap/foundation-testing/e2e';
import { test } from '../fixture';

test('expected page title', async ({ page }: { page: any }) => {
  await expect(page).toHaveTitle(/Howto Consolidators/);
});
