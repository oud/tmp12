import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandeXRM } from '../demande-xrm.model';
import { DemandeXRMService } from '../service/demande-xrm.service';

const demandeXRMResolve = (route: ActivatedRouteSnapshot): Observable<null | IDemandeXRM> => {
  const id = route.params.id;
  if (id) {
    return inject(DemandeXRMService)
      .find(id)
      .pipe(
        mergeMap((demandeXRM: HttpResponse<IDemandeXRM>) => {
          if (demandeXRM.body) {
            return of(demandeXRM.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default demandeXRMResolve;
