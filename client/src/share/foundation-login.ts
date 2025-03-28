import { configure } from '@genesislcap/foundation-auth/config';
import { Connect } from '@genesislcap/foundation-comms';
import { API_HOST } from '@genesislcap/foundation-utils';
import { getUser } from '@genesislcap/foundation-user';
import { DI } from '@genesislcap/web-core';
import { AUTH_PATH } from '@/config';
import type { Router } from '@/utils/history';

/**
 * Configure the micro frontend
 */
export const configureFoundationLogin = ({ router }:{ router: Router }) => {
  const connect = DI.getOrCreateDOMContainer().get(Connect);

  return configure({
    name: 'client-app-login',
    omitRoutes: ['request-account', 'forgot-password'],
    hostPath: AUTH_PATH,
    // The router accepts the WebSocket only once the HTTP login has set its session cookie
    postLoginRedirect: async () => {
      await connect.connect(API_HOST);
      const lastPath = getUser().lastPath() === '/' ? undefined : getUser().lastPath();
      router.push(lastPath ?? '/home');
    },
  });
}
