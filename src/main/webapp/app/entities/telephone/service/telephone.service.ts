import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITelephone, NewTelephone } from '../telephone.model';

export type PartialUpdateTelephone = Partial<ITelephone> & Pick<ITelephone, 'id'>;

type RestOf<T extends ITelephone | NewTelephone> = Omit<T, 'dateDerniereModification'> & {
  dateDerniereModification?: string | null;
};

export type RestTelephone = RestOf<ITelephone>;

export type NewRestTelephone = RestOf<NewTelephone>;

export type PartialUpdateRestTelephone = RestOf<PartialUpdateTelephone>;

export type EntityResponseType = HttpResponse<ITelephone>;
export type EntityArrayResponseType = HttpResponse<ITelephone[]>;

@Injectable({ providedIn: 'root' })
export class TelephoneService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/telephones');

  create(telephone: NewTelephone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(telephone);
    return this.http
      .post<RestTelephone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(telephone: ITelephone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(telephone);
    return this.http
      .put<RestTelephone>(`${this.resourceUrl}/${this.getTelephoneIdentifier(telephone)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(telephone: PartialUpdateTelephone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(telephone);
    return this.http
      .patch<RestTelephone>(`${this.resourceUrl}/${this.getTelephoneIdentifier(telephone)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTelephone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTelephone[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTelephoneIdentifier(telephone: Pick<ITelephone, 'id'>): number {
    return telephone.id;
  }

  compareTelephone(o1: Pick<ITelephone, 'id'> | null, o2: Pick<ITelephone, 'id'> | null): boolean {
    return o1 && o2 ? this.getTelephoneIdentifier(o1) === this.getTelephoneIdentifier(o2) : o1 === o2;
  }

  addTelephoneToCollectionIfMissing<Type extends Pick<ITelephone, 'id'>>(
    telephoneCollection: Type[],
    ...telephonesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const telephones: Type[] = telephonesToCheck.filter(isPresent);
    if (telephones.length > 0) {
      const telephoneCollectionIdentifiers = telephoneCollection.map(telephoneItem => this.getTelephoneIdentifier(telephoneItem));
      const telephonesToAdd = telephones.filter(telephoneItem => {
        const telephoneIdentifier = this.getTelephoneIdentifier(telephoneItem);
        if (telephoneCollectionIdentifiers.includes(telephoneIdentifier)) {
          return false;
        }
        telephoneCollectionIdentifiers.push(telephoneIdentifier);
        return true;
      });
      return [...telephonesToAdd, ...telephoneCollection];
    }
    return telephoneCollection;
  }

  protected convertDateFromClient<T extends ITelephone | NewTelephone | PartialUpdateTelephone>(telephone: T): RestOf<T> {
    return {
      ...telephone,
      dateDerniereModification: telephone.dateDerniereModification?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTelephone: RestTelephone): ITelephone {
    return {
      ...restTelephone,
      dateDerniereModification: restTelephone.dateDerniereModification ? dayjs(restTelephone.dateDerniereModification) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTelephone>): HttpResponse<ITelephone> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTelephone[]>): HttpResponse<ITelephone[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
