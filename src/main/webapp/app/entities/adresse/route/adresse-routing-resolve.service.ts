import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdresse } from '../adresse.model';
import { AdresseService } from '../service/adresse.service';

const adresseResolve = (route: ActivatedRouteSnapshot): Observable<null | IAdresse> => {
  const id = route.params.id;
  if (id) {
    return inject(AdresseService)
      .find(id)
      .pipe(
        mergeMap((adresse: HttpResponse<IAdresse>) => {
          if (adresse.body) {
            return of(adresse.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default adresseResolve;
