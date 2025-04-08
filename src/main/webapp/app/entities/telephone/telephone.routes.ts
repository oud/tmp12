import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import TelephoneResolve from './route/telephone-routing-resolve.service';

const telephoneRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/telephone.component').then(m => m.TelephoneComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/telephone-detail.component').then(m => m.TelephoneDetailComponent),
    resolve: {
      telephone: TelephoneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/telephone-update.component').then(m => m.TelephoneUpdateComponent),
    resolve: {
      telephone: TelephoneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/telephone-update.component').then(m => m.TelephoneUpdateComponent),
    resolve: {
      telephone: TelephoneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default telephoneRoute;
