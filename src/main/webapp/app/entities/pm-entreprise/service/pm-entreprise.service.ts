import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPmEntreprise, NewPmEntreprise } from '../pm-entreprise.model';

export type PartialUpdatePmEntreprise = Partial<IPmEntreprise> & Pick<IPmEntreprise, 'id'>;

type RestOf<T extends IPmEntreprise | NewPmEntreprise> = Omit<
  T,
  'dateCreationJuridique' | 'dateEtat' | 'dateFermetureJuridique' | 'dateCessationActivite' | 'dateEffectifOfficiel'
> & {
  dateCreationJuridique?: string | null;
  dateEtat?: string | null;
  dateFermetureJuridique?: string | null;
  dateCessationActivite?: string | null;
  dateEffectifOfficiel?: string | null;
};

export type RestPmEntreprise = RestOf<IPmEntreprise>;

export type NewRestPmEntreprise = RestOf<NewPmEntreprise>;

export type PartialUpdateRestPmEntreprise = RestOf<PartialUpdatePmEntreprise>;

export type EntityResponseType = HttpResponse<IPmEntreprise>;
export type EntityArrayResponseType = HttpResponse<IPmEntreprise[]>;

@Injectable({ providedIn: 'root' })
export class PmEntrepriseService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pm-entreprises');

  create(pmEntreprise: NewPmEntreprise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pmEntreprise);
    return this.http
      .post<RestPmEntreprise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(pmEntreprise: IPmEntreprise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pmEntreprise);
    return this.http
      .put<RestPmEntreprise>(`${this.resourceUrl}/${this.getPmEntrepriseIdentifier(pmEntreprise)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(pmEntreprise: PartialUpdatePmEntreprise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pmEntreprise);
    return this.http
      .patch<RestPmEntreprise>(`${this.resourceUrl}/${this.getPmEntrepriseIdentifier(pmEntreprise)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPmEntreprise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPmEntreprise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPmEntrepriseIdentifier(pmEntreprise: Pick<IPmEntreprise, 'id'>): number {
    return pmEntreprise.id;
  }

  comparePmEntreprise(o1: Pick<IPmEntreprise, 'id'> | null, o2: Pick<IPmEntreprise, 'id'> | null): boolean {
    return o1 && o2 ? this.getPmEntrepriseIdentifier(o1) === this.getPmEntrepriseIdentifier(o2) : o1 === o2;
  }

  addPmEntrepriseToCollectionIfMissing<Type extends Pick<IPmEntreprise, 'id'>>(
    pmEntrepriseCollection: Type[],
    ...pmEntreprisesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pmEntreprises: Type[] = pmEntreprisesToCheck.filter(isPresent);
    if (pmEntreprises.length > 0) {
      const pmEntrepriseCollectionIdentifiers = pmEntrepriseCollection.map(pmEntrepriseItem =>
        this.getPmEntrepriseIdentifier(pmEntrepriseItem),
      );
      const pmEntreprisesToAdd = pmEntreprises.filter(pmEntrepriseItem => {
        const pmEntrepriseIdentifier = this.getPmEntrepriseIdentifier(pmEntrepriseItem);
        if (pmEntrepriseCollectionIdentifiers.includes(pmEntrepriseIdentifier)) {
          return false;
        }
        pmEntrepriseCollectionIdentifiers.push(pmEntrepriseIdentifier);
        return true;
      });
      return [...pmEntreprisesToAdd, ...pmEntrepriseCollection];
    }
    return pmEntrepriseCollection;
  }

  protected convertDateFromClient<T extends IPmEntreprise | NewPmEntreprise | PartialUpdatePmEntreprise>(pmEntreprise: T): RestOf<T> {
    return {
      ...pmEntreprise,
      dateCreationJuridique: pmEntreprise.dateCreationJuridique?.format(DATE_FORMAT) ?? null,
      dateEtat: pmEntreprise.dateEtat?.format(DATE_FORMAT) ?? null,
      dateFermetureJuridique: pmEntreprise.dateFermetureJuridique?.format(DATE_FORMAT) ?? null,
      dateCessationActivite: pmEntreprise.dateCessationActivite?.format(DATE_FORMAT) ?? null,
      dateEffectifOfficiel: pmEntreprise.dateEffectifOfficiel?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPmEntreprise: RestPmEntreprise): IPmEntreprise {
    return {
      ...restPmEntreprise,
      dateCreationJuridique: restPmEntreprise.dateCreationJuridique ? dayjs(restPmEntreprise.dateCreationJuridique) : undefined,
      dateEtat: restPmEntreprise.dateEtat ? dayjs(restPmEntreprise.dateEtat) : undefined,
      dateFermetureJuridique: restPmEntreprise.dateFermetureJuridique ? dayjs(restPmEntreprise.dateFermetureJuridique) : undefined,
      dateCessationActivite: restPmEntreprise.dateCessationActivite ? dayjs(restPmEntreprise.dateCessationActivite) : undefined,
      dateEffectifOfficiel: restPmEntreprise.dateEffectifOfficiel ? dayjs(restPmEntreprise.dateEffectifOfficiel) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPmEntreprise>): HttpResponse<IPmEntreprise> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPmEntreprise[]>): HttpResponse<IPmEntreprise[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
