import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MiseEnGestionResolve from './route/mise-en-gestion-routing-resolve.service';

const miseEnGestionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/mise-en-gestion.component').then(m => m.MiseEnGestionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/mise-en-gestion-detail.component').then(m => m.MiseEnGestionDetailComponent),
    resolve: {
      miseEnGestion: MiseEnGestionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/mise-en-gestion-update.component').then(m => m.MiseEnGestionUpdateComponent),
    resolve: {
      miseEnGestion: MiseEnGestionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/mise-en-gestion-update.component').then(m => m.MiseEnGestionUpdateComponent),
    resolve: {
      miseEnGestion: MiseEnGestionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default miseEnGestionRoute;
