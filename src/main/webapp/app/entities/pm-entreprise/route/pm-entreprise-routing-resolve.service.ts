import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPmEntreprise } from '../pm-entreprise.model';
import { PmEntrepriseService } from '../service/pm-entreprise.service';

const pmEntrepriseResolve = (route: ActivatedRouteSnapshot): Observable<null | IPmEntreprise> => {
  const id = route.params.id;
  if (id) {
    return inject(PmEntrepriseService)
      .find(id)
      .pipe(
        mergeMap((pmEntreprise: HttpResponse<IPmEntreprise>) => {
          if (pmEntreprise.body) {
            return of(pmEntreprise.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pmEntrepriseResolve;
