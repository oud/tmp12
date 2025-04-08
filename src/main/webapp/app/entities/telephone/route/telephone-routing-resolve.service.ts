import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITelephone } from '../telephone.model';
import { TelephoneService } from '../service/telephone.service';

const telephoneResolve = (route: ActivatedRouteSnapshot): Observable<null | ITelephone> => {
  const id = route.params.id;
  if (id) {
    return inject(TelephoneService)
      .find(id)
      .pipe(
        mergeMap((telephone: HttpResponse<ITelephone>) => {
          if (telephone.body) {
            return of(telephone.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default telephoneResolve;
