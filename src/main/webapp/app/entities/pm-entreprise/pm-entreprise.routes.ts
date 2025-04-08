import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PmEntrepriseResolve from './route/pm-entreprise-routing-resolve.service';

const pmEntrepriseRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/pm-entreprise.component').then(m => m.PmEntrepriseComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/pm-entreprise-detail.component').then(m => m.PmEntrepriseDetailComponent),
    resolve: {
      pmEntreprise: PmEntrepriseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/pm-entreprise-update.component').then(m => m.PmEntrepriseUpdateComponent),
    resolve: {
      pmEntreprise: PmEntrepriseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/pm-entreprise-update.component').then(m => m.PmEntrepriseUpdateComponent),
    resolve: {
      pmEntreprise: PmEntrepriseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pmEntrepriseRoute;
