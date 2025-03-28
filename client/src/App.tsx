import { useEffect, useState } from 'react';
import { unstable_HistoryRouter as HistoryRouter, Routes, Route, useLocation } from 'react-router-dom';
import {
  history,
  setApiHost,
  getLayoutNameByRoute,
} from './utils';
import { customEventFactory, registerStylesTarget } from '@/pbc/utils';
import LayoutWrapper from './layouts/LayoutWrapper';
import LayoutName from '@/types/LayoutName';
import { AUTH_PATH, routeLayouts } from './config';
import { AuthProvider } from './store/AuthContext';
import { RoutesProvider, useRoutesContext } from './store/RoutesContext';
import AuthPage from './pages/AuthPage/AuthPage';
import { registerComponents as genesisRegisterComponents } from './share/genesis-components';
import { configureFoundationLogin } from './share/foundation-login';
import ProtectedGuard from './guards/ProtectedGuard';
import { storeService } from '@/services/store.service';

const DynamicLayout = () => {
  const location = useLocation();
  const [layoutName, setLayoutName] = useState<LayoutName>(routeLayouts[location.pathname]  || 'default');
  const handleRouteChange = (location: any) => {
    setLayoutName(getLayoutNameByRoute(location.pathname));
  };
  const route = useRoutesContext().find((r) => r.path === location.pathname);
  let pageComponent;
  let content;

  useEffect(() => {
    handleRouteChange(location);
    const unlisten = history.listen(handleRouteChange);

    return () => {
      unlisten();
    }
  }, [location]);

  if (route) {
    pageComponent = route.element;
  } else {
    pageComponent = <AuthPage />;
  }

  if (location.pathname === `/${AUTH_PATH}` || location.pathname === '/') {
    content = pageComponent;
  } else {
    content = <ProtectedGuard>{pageComponent}</ProtectedGuard>
  }

  return <LayoutWrapper layout={layoutName}>{content}</LayoutWrapper>

};

interface AppProps {
  rootElement: HTMLElement;
}

const App: React.FC<AppProps> = ({ rootElement }) => {
  const [isStoreConnected, setIsStoreConnected] = useState(false);
  const dispatchCustomEvent = (type: string, detail?: any) => {
    rootElement.dispatchEvent(customEventFactory(type, detail));
  };
  const handleStoreConnected = (event: CustomEvent) => {
    storeService.onConnected(event);
  };

  setApiHost();
  genesisRegisterComponents();
  configureFoundationLogin({ router: history });

  useEffect(() => {
    registerStylesTarget(document.body, 'main');
    if (!isStoreConnected) {
      rootElement.addEventListener('store-connected', handleStoreConnected);
      dispatchCustomEvent('store-connected', rootElement);
      dispatchCustomEvent('store-ready', true);
      setIsStoreConnected(true);
    }

    return () => {
      if (isStoreConnected) {
        rootElement.removeEventListener('store-connected', handleStoreConnected);
        dispatchCustomEvent('store-disconnected');
      }
    };
  }, [isStoreConnected]);

  return (
    <AuthProvider>
      <RoutesProvider>
        <HistoryRouter history={history as any}>
          <Routes>
            <Route path="*" element={<DynamicLayout />} />
          </Routes>
        </HistoryRouter>
      </RoutesProvider>
    </AuthProvider>
  );
};

export default App;