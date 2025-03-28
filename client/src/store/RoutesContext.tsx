import React, { createContext, useContext, ReactNode } from 'react';
import { RouteObject, Navigate } from 'react-router-dom';
import { getApp } from '@genesislcap/foundation-shell/app';
import AuthPage from '@/pages/AuthPage/AuthPage';
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage';
import NotPermittedPage from '@/pages/NotPermittedPage/NotPermittedPage';
import Home from '@/pages/Home/Home';
import Example_1 from '@/pages/Example_1/Example_1';
import Example_2 from '@/pages/Example_2/Example_2';
import Example_3 from '@/pages/Example_3/Example_3';
import PBCContainer from '@/pbc/container';
import { AUTH_PATH, NOT_PERMITTED_PATH } from '@/config';
import TradePage from '@/pages/TradePage/TradePage';

const routes = [
  {
    path: '',
    element: <Navigate to={AUTH_PATH} replace />,
  },
  {
    path: '/not-found',
    element: <NotFoundPage />,
  },
  {
    path: `/${AUTH_PATH}`,
    element: <AuthPage />,
  },
  {
    path: `/${NOT_PERMITTED_PATH}`,
    element: <NotPermittedPage />,
  },
  {
    path: '/home',
    element: <Home />,
    data: {
      permissionCode: '',
      navItems: [
        {
          navId: 'header',
          title: 'HOME',
          icon: {
            name: '',
            variant: 'solid',
          },
        },
      ],
    },
  },
  {
    path: '/Trades',
    element: <TradePage />,
    data: {
      permissionCode: '',
      navItems: [
        {
          navId: 'header',
          title: 'Trades',
          icon: {
            name: '',
            variant: 'solid',
          },
        },
      ],
    },
  },
  {
    path: '/example-1',
    element: <Example_1 />,
    data: {
      permissionCode: '',
      navItems: [
        {
          navId: 'header',
          title: 'Example 1',
          icon: {
            name: '',
            variant: 'solid',
          },
        },
      ],
    },
  },
  {
    path: '/example-2',
    element: <Example_2 />,
    data: {
      permissionCode: '',
      navItems: [
        {
          navId: 'header',
          title: 'Example 2',
          icon: {
            name: '',
            variant: 'solid',
          },
        },
      ],
    },
  },
  {
    path: '/example-3',
    element: <Example_3 />,
    data: {
      permissionCode: '',
      navItems: [
        {
          navId: 'header',
          title: 'Example 3',
          icon: {
            name: '',
            variant: 'solid',
          },
        },
      ],
    },
  }
];

const RoutesContext = createContext<RouteObject[]>([]);

export const RoutesProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const pbcRoutes = getApp().routes.map((route) => ({
    title: route.title,
    path: `/${route.path}`,
    element: <PBCContainer />,
    data: {
      ...route.settings,
      pbcElement: route.element,
      // @ts-expect-error - getApp() is not typed to return the elementTag
      pbcElementTag: route.elementTag,
      navItems: route.navItems,
    },
  }));

  const allRoutes = [...routes, ...pbcRoutes];

  return <RoutesContext.Provider value={allRoutes}>{children}</RoutesContext.Provider>;
};

export const useRoutesContext = () => {
  return useContext(RoutesContext);
};