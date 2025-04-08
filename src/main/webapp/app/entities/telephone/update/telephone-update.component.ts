import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPmEntreprise } from 'app/entities/pm-entreprise/pm-entreprise.model';
import { PmEntrepriseService } from 'app/entities/pm-entreprise/service/pm-entreprise.service';
import { IPmEtablissement } from 'app/entities/pm-etablissement/pm-etablissement.model';
import { PmEtablissementService } from 'app/entities/pm-etablissement/service/pm-etablissement.service';
import { TelephoneService } from '../service/telephone.service';
import { ITelephone } from '../telephone.model';
import { TelephoneFormGroup, TelephoneFormService } from './telephone-form.service';

@Component({
  selector: 'jhi-telephone-update',
  templateUrl: './telephone-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TelephoneUpdateComponent implements OnInit {
  isSaving = false;
  telephone: ITelephone | null = null;

  pmEntreprisesSharedCollection: IPmEntreprise[] = [];
  pmEtablissementsSharedCollection: IPmEtablissement[] = [];

  protected telephoneService = inject(TelephoneService);
  protected telephoneFormService = inject(TelephoneFormService);
  protected pmEntrepriseService = inject(PmEntrepriseService);
  protected pmEtablissementService = inject(PmEtablissementService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TelephoneFormGroup = this.telephoneFormService.createTelephoneFormGroup();

  comparePmEntreprise = (o1: IPmEntreprise | null, o2: IPmEntreprise | null): boolean =>
    this.pmEntrepriseService.comparePmEntreprise(o1, o2);

  comparePmEtablissement = (o1: IPmEtablissement | null, o2: IPmEtablissement | null): boolean =>
    this.pmEtablissementService.comparePmEtablissement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ telephone }) => {
      this.telephone = telephone;
      if (telephone) {
        this.updateForm(telephone);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const telephone = this.telephoneFormService.getTelephone(this.editForm);
    if (telephone.id !== null) {
      this.subscribeToSaveResponse(this.telephoneService.update(telephone));
    } else {
      this.subscribeToSaveResponse(this.telephoneService.create(telephone));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITelephone>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(telephone: ITelephone): void {
    this.telephone = telephone;
    this.telephoneFormService.resetForm(this.editForm, telephone);

    this.pmEntreprisesSharedCollection = this.pmEntrepriseService.addPmEntrepriseToCollectionIfMissing<IPmEntreprise>(
      this.pmEntreprisesSharedCollection,
      telephone.pmEntreprise,
    );
    this.pmEtablissementsSharedCollection = this.pmEtablissementService.addPmEtablissementToCollectionIfMissing<IPmEtablissement>(
      this.pmEtablissementsSharedCollection,
      telephone.pmEtablissement,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pmEntrepriseService
      .query()
      .pipe(map((res: HttpResponse<IPmEntreprise[]>) => res.body ?? []))
      .pipe(
        map((pmEntreprises: IPmEntreprise[]) =>
          this.pmEntrepriseService.addPmEntrepriseToCollectionIfMissing<IPmEntreprise>(pmEntreprises, this.telephone?.pmEntreprise),
        ),
      )
      .subscribe((pmEntreprises: IPmEntreprise[]) => (this.pmEntreprisesSharedCollection = pmEntreprises));

    this.pmEtablissementService
      .query()
      .pipe(map((res: HttpResponse<IPmEtablissement[]>) => res.body ?? []))
      .pipe(
        map((pmEtablissements: IPmEtablissement[]) =>
          this.pmEtablissementService.addPmEtablissementToCollectionIfMissing<IPmEtablissement>(
            pmEtablissements,
            this.telephone?.pmEtablissement,
          ),
        ),
      )
      .subscribe((pmEtablissements: IPmEtablissement[]) => (this.pmEtablissementsSharedCollection = pmEtablissements));
  }
}
