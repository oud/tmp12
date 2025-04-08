import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMiseEnGestion, NewMiseEnGestion } from '../mise-en-gestion.model';

export type PartialUpdateMiseEnGestion = Partial<IMiseEnGestion> & Pick<IMiseEnGestion, 'id'>;

type RestOf<T extends IMiseEnGestion | NewMiseEnGestion> = Omit<T, 'dateEffet'> & {
  dateEffet?: string | null;
};

export type RestMiseEnGestion = RestOf<IMiseEnGestion>;

export type NewRestMiseEnGestion = RestOf<NewMiseEnGestion>;

export type PartialUpdateRestMiseEnGestion = RestOf<PartialUpdateMiseEnGestion>;

export type EntityResponseType = HttpResponse<IMiseEnGestion>;
export type EntityArrayResponseType = HttpResponse<IMiseEnGestion[]>;

@Injectable({ providedIn: 'root' })
export class MiseEnGestionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mise-en-gestions');

  create(miseEnGestion: NewMiseEnGestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(miseEnGestion);
    return this.http
      .post<RestMiseEnGestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(miseEnGestion: IMiseEnGestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(miseEnGestion);
    return this.http
      .put<RestMiseEnGestion>(`${this.resourceUrl}/${this.getMiseEnGestionIdentifier(miseEnGestion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(miseEnGestion: PartialUpdateMiseEnGestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(miseEnGestion);
    return this.http
      .patch<RestMiseEnGestion>(`${this.resourceUrl}/${this.getMiseEnGestionIdentifier(miseEnGestion)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMiseEnGestion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMiseEnGestion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMiseEnGestionIdentifier(miseEnGestion: Pick<IMiseEnGestion, 'id'>): number {
    return miseEnGestion.id;
  }

  compareMiseEnGestion(o1: Pick<IMiseEnGestion, 'id'> | null, o2: Pick<IMiseEnGestion, 'id'> | null): boolean {
    return o1 && o2 ? this.getMiseEnGestionIdentifier(o1) === this.getMiseEnGestionIdentifier(o2) : o1 === o2;
  }

  addMiseEnGestionToCollectionIfMissing<Type extends Pick<IMiseEnGestion, 'id'>>(
    miseEnGestionCollection: Type[],
    ...miseEnGestionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const miseEnGestions: Type[] = miseEnGestionsToCheck.filter(isPresent);
    if (miseEnGestions.length > 0) {
      const miseEnGestionCollectionIdentifiers = miseEnGestionCollection.map(miseEnGestionItem =>
        this.getMiseEnGestionIdentifier(miseEnGestionItem),
      );
      const miseEnGestionsToAdd = miseEnGestions.filter(miseEnGestionItem => {
        const miseEnGestionIdentifier = this.getMiseEnGestionIdentifier(miseEnGestionItem);
        if (miseEnGestionCollectionIdentifiers.includes(miseEnGestionIdentifier)) {
          return false;
        }
        miseEnGestionCollectionIdentifiers.push(miseEnGestionIdentifier);
        return true;
      });
      return [...miseEnGestionsToAdd, ...miseEnGestionCollection];
    }
    return miseEnGestionCollection;
  }

  protected convertDateFromClient<T extends IMiseEnGestion | NewMiseEnGestion | PartialUpdateMiseEnGestion>(miseEnGestion: T): RestOf<T> {
    return {
      ...miseEnGestion,
      dateEffet: miseEnGestion.dateEffet?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMiseEnGestion: RestMiseEnGestion): IMiseEnGestion {
    return {
      ...restMiseEnGestion,
      dateEffet: restMiseEnGestion.dateEffet ? dayjs(restMiseEnGestion.dateEffet) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMiseEnGestion>): HttpResponse<IMiseEnGestion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMiseEnGestion[]>): HttpResponse<IMiseEnGestion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
