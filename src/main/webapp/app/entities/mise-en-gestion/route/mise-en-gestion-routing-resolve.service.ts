import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMiseEnGestion } from '../mise-en-gestion.model';
import { MiseEnGestionService } from '../service/mise-en-gestion.service';

const miseEnGestionResolve = (route: ActivatedRouteSnapshot): Observable<null | IMiseEnGestion> => {
  const id = route.params.id;
  if (id) {
    return inject(MiseEnGestionService)
      .find(id)
      .pipe(
        mergeMap((miseEnGestion: HttpResponse<IMiseEnGestion>) => {
          if (miseEnGestion.body) {
            return of(miseEnGestion.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default miseEnGestionResolve;
