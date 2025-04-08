import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPmEtablissement, NewPmEtablissement } from '../pm-etablissement.model';

export type PartialUpdatePmEtablissement = Partial<IPmEtablissement> & Pick<IPmEtablissement, 'id'>;

export type EntityResponseType = HttpResponse<IPmEtablissement>;
export type EntityArrayResponseType = HttpResponse<IPmEtablissement[]>;

@Injectable({ providedIn: 'root' })
export class PmEtablissementService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pm-etablissements');

  create(pmEtablissement: NewPmEtablissement): Observable<EntityResponseType> {
    return this.http.post<IPmEtablissement>(this.resourceUrl, pmEtablissement, { observe: 'response' });
  }

  update(pmEtablissement: IPmEtablissement): Observable<EntityResponseType> {
    return this.http.put<IPmEtablissement>(`${this.resourceUrl}/${this.getPmEtablissementIdentifier(pmEtablissement)}`, pmEtablissement, {
      observe: 'response',
    });
  }

  partialUpdate(pmEtablissement: PartialUpdatePmEtablissement): Observable<EntityResponseType> {
    return this.http.patch<IPmEtablissement>(`${this.resourceUrl}/${this.getPmEtablissementIdentifier(pmEtablissement)}`, pmEtablissement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPmEtablissement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPmEtablissement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPmEtablissementIdentifier(pmEtablissement: Pick<IPmEtablissement, 'id'>): number {
    return pmEtablissement.id;
  }

  comparePmEtablissement(o1: Pick<IPmEtablissement, 'id'> | null, o2: Pick<IPmEtablissement, 'id'> | null): boolean {
    return o1 && o2 ? this.getPmEtablissementIdentifier(o1) === this.getPmEtablissementIdentifier(o2) : o1 === o2;
  }

  addPmEtablissementToCollectionIfMissing<Type extends Pick<IPmEtablissement, 'id'>>(
    pmEtablissementCollection: Type[],
    ...pmEtablissementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pmEtablissements: Type[] = pmEtablissementsToCheck.filter(isPresent);
    if (pmEtablissements.length > 0) {
      const pmEtablissementCollectionIdentifiers = pmEtablissementCollection.map(pmEtablissementItem =>
        this.getPmEtablissementIdentifier(pmEtablissementItem),
      );
      const pmEtablissementsToAdd = pmEtablissements.filter(pmEtablissementItem => {
        const pmEtablissementIdentifier = this.getPmEtablissementIdentifier(pmEtablissementItem);
        if (pmEtablissementCollectionIdentifiers.includes(pmEtablissementIdentifier)) {
          return false;
        }
        pmEtablissementCollectionIdentifiers.push(pmEtablissementIdentifier);
        return true;
      });
      return [...pmEtablissementsToAdd, ...pmEtablissementCollection];
    }
    return pmEtablissementCollection;
  }
}
