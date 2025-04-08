import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AdresseResolve from './route/adresse-routing-resolve.service';

const adresseRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/adresse.component').then(m => m.AdresseComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/adresse-detail.component').then(m => m.AdresseDetailComponent),
    resolve: {
      adresse: AdresseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/adresse-update.component').then(m => m.AdresseUpdateComponent),
    resolve: {
      adresse: AdresseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/adresse-update.component').then(m => m.AdresseUpdateComponent),
    resolve: {
      adresse: AdresseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default adresseRoute;
