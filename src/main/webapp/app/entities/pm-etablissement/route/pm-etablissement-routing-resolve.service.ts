import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPmEtablissement } from '../pm-etablissement.model';
import { PmEtablissementService } from '../service/pm-etablissement.service';

const pmEtablissementResolve = (route: ActivatedRouteSnapshot): Observable<null | IPmEtablissement> => {
  const id = route.params.id;
  if (id) {
    return inject(PmEtablissementService)
      .find(id)
      .pipe(
        mergeMap((pmEtablissement: HttpResponse<IPmEtablissement>) => {
          if (pmEtablissement.body) {
            return of(pmEtablissement.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pmEtablissementResolve;
