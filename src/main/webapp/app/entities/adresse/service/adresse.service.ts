import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdresse, NewAdresse } from '../adresse.model';

export type PartialUpdateAdresse = Partial<IAdresse> & Pick<IAdresse, 'id'>;

type RestOf<T extends IAdresse | NewAdresse> = Omit<T, 'dateDerniereModification'> & {
  dateDerniereModification?: string | null;
};

export type RestAdresse = RestOf<IAdresse>;

export type NewRestAdresse = RestOf<NewAdresse>;

export type PartialUpdateRestAdresse = RestOf<PartialUpdateAdresse>;

export type EntityResponseType = HttpResponse<IAdresse>;
export type EntityArrayResponseType = HttpResponse<IAdresse[]>;

@Injectable({ providedIn: 'root' })
export class AdresseService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adresses');

  create(adresse: NewAdresse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adresse);
    return this.http
      .post<RestAdresse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(adresse: IAdresse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adresse);
    return this.http
      .put<RestAdresse>(`${this.resourceUrl}/${this.getAdresseIdentifier(adresse)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(adresse: PartialUpdateAdresse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(adresse);
    return this.http
      .patch<RestAdresse>(`${this.resourceUrl}/${this.getAdresseIdentifier(adresse)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAdresse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAdresse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAdresseIdentifier(adresse: Pick<IAdresse, 'id'>): number {
    return adresse.id;
  }

  compareAdresse(o1: Pick<IAdresse, 'id'> | null, o2: Pick<IAdresse, 'id'> | null): boolean {
    return o1 && o2 ? this.getAdresseIdentifier(o1) === this.getAdresseIdentifier(o2) : o1 === o2;
  }

  addAdresseToCollectionIfMissing<Type extends Pick<IAdresse, 'id'>>(
    adresseCollection: Type[],
    ...adressesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const adresses: Type[] = adressesToCheck.filter(isPresent);
    if (adresses.length > 0) {
      const adresseCollectionIdentifiers = adresseCollection.map(adresseItem => this.getAdresseIdentifier(adresseItem));
      const adressesToAdd = adresses.filter(adresseItem => {
        const adresseIdentifier = this.getAdresseIdentifier(adresseItem);
        if (adresseCollectionIdentifiers.includes(adresseIdentifier)) {
          return false;
        }
        adresseCollectionIdentifiers.push(adresseIdentifier);
        return true;
      });
      return [...adressesToAdd, ...adresseCollection];
    }
    return adresseCollection;
  }

  protected convertDateFromClient<T extends IAdresse | NewAdresse | PartialUpdateAdresse>(adresse: T): RestOf<T> {
    return {
      ...adresse,
      dateDerniereModification: adresse.dateDerniereModification?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAdresse: RestAdresse): IAdresse {
    return {
      ...restAdresse,
      dateDerniereModification: restAdresse.dateDerniereModification ? dayjs(restAdresse.dateDerniereModification) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAdresse>): HttpResponse<IAdresse> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAdresse[]>): HttpResponse<IAdresse[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
