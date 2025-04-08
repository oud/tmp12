import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DemandeXRMResolve from './route/demande-xrm-routing-resolve.service';

const demandeXRMRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/demande-xrm.component').then(m => m.DemandeXRMComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/demande-xrm-detail.component').then(m => m.DemandeXRMDetailComponent),
    resolve: {
      demandeXRM: DemandeXRMResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/demande-xrm-update.component').then(m => m.DemandeXRMUpdateComponent),
    resolve: {
      demandeXRM: DemandeXRMResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/demande-xrm-update.component').then(m => m.DemandeXRMUpdateComponent),
    resolve: {
      demandeXRM: DemandeXRMResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default demandeXRMRoute;
