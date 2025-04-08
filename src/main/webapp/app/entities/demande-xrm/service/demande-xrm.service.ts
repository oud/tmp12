import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandeXRM, NewDemandeXRM } from '../demande-xrm.model';

export type PartialUpdateDemandeXRM = Partial<IDemandeXRM> & Pick<IDemandeXRM, 'id'>;

export type EntityResponseType = HttpResponse<IDemandeXRM>;
export type EntityArrayResponseType = HttpResponse<IDemandeXRM[]>;

@Injectable({ providedIn: 'root' })
export class DemandeXRMService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-xrms');

  create(demandeXRM: NewDemandeXRM): Observable<EntityResponseType> {
    return this.http.post<IDemandeXRM>(this.resourceUrl, demandeXRM, { observe: 'response' });
  }

  update(demandeXRM: IDemandeXRM): Observable<EntityResponseType> {
    return this.http.put<IDemandeXRM>(`${this.resourceUrl}/${this.getDemandeXRMIdentifier(demandeXRM)}`, demandeXRM, {
      observe: 'response',
    });
  }

  partialUpdate(demandeXRM: PartialUpdateDemandeXRM): Observable<EntityResponseType> {
    return this.http.patch<IDemandeXRM>(`${this.resourceUrl}/${this.getDemandeXRMIdentifier(demandeXRM)}`, demandeXRM, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemandeXRM>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeXRM[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemandeXRMIdentifier(demandeXRM: Pick<IDemandeXRM, 'id'>): number {
    return demandeXRM.id;
  }

  compareDemandeXRM(o1: Pick<IDemandeXRM, 'id'> | null, o2: Pick<IDemandeXRM, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemandeXRMIdentifier(o1) === this.getDemandeXRMIdentifier(o2) : o1 === o2;
  }

  addDemandeXRMToCollectionIfMissing<Type extends Pick<IDemandeXRM, 'id'>>(
    demandeXRMCollection: Type[],
    ...demandeXRMSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demandeXRMS: Type[] = demandeXRMSToCheck.filter(isPresent);
    if (demandeXRMS.length > 0) {
      const demandeXRMCollectionIdentifiers = demandeXRMCollection.map(demandeXRMItem => this.getDemandeXRMIdentifier(demandeXRMItem));
      const demandeXRMSToAdd = demandeXRMS.filter(demandeXRMItem => {
        const demandeXRMIdentifier = this.getDemandeXRMIdentifier(demandeXRMItem);
        if (demandeXRMCollectionIdentifiers.includes(demandeXRMIdentifier)) {
          return false;
        }
        demandeXRMCollectionIdentifiers.push(demandeXRMIdentifier);
        return true;
      });
      return [...demandeXRMSToAdd, ...demandeXRMCollection];
    }
    return demandeXRMCollection;
  }
}
