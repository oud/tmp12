import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PmEtablissementResolve from './route/pm-etablissement-routing-resolve.service';

const pmEtablissementRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/pm-etablissement.component').then(m => m.PmEtablissementComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/pm-etablissement-detail.component').then(m => m.PmEtablissementDetailComponent),
    resolve: {
      pmEtablissement: PmEtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/pm-etablissement-update.component').then(m => m.PmEtablissementUpdateComponent),
    resolve: {
      pmEtablissement: PmEtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/pm-etablissement-update.component').then(m => m.PmEtablissementUpdateComponent),
    resolve: {
      pmEtablissement: PmEtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pmEtablissementRoute;
