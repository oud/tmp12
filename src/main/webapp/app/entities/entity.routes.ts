import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'tmp12App.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'demande-xrm',
    data: { pageTitle: 'tmp12App.demandeXRM.home.title' },
    loadChildren: () => import('./demande-xrm/demande-xrm.routes'),
  },
  {
    path: 'mise-en-gestion',
    data: { pageTitle: 'tmp12App.miseEnGestion.home.title' },
    loadChildren: () => import('./mise-en-gestion/mise-en-gestion.routes'),
  },
  {
    path: 'pm-entreprise',
    data: { pageTitle: 'tmp12App.pmEntreprise.home.title' },
    loadChildren: () => import('./pm-entreprise/pm-entreprise.routes'),
  },
  {
    path: 'adresse',
    data: { pageTitle: 'tmp12App.adresse.home.title' },
    loadChildren: () => import('./adresse/adresse.routes'),
  },
  {
    path: 'telephone',
    data: { pageTitle: 'tmp12App.telephone.home.title' },
    loadChildren: () => import('./telephone/telephone.routes'),
  },
  {
    path: 'email',
    data: { pageTitle: 'tmp12App.email.home.title' },
    loadChildren: () => import('./email/email.routes'),
  },
  {
    path: 'pm-etablissement',
    data: { pageTitle: 'tmp12App.pmEtablissement.home.title' },
    loadChildren: () => import('./pm-etablissement/pm-etablissement.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
