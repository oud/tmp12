import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPmEntreprise } from 'app/entities/pm-entreprise/pm-entreprise.model';
import { PmEntrepriseService } from 'app/entities/pm-entreprise/service/pm-entreprise.service';
import { IMiseEnGestion } from 'app/entities/mise-en-gestion/mise-en-gestion.model';
import { MiseEnGestionService } from 'app/entities/mise-en-gestion/service/mise-en-gestion.service';
import { DemandeXRMService } from '../service/demande-xrm.service';
import { IDemandeXRM } from '../demande-xrm.model';
import { DemandeXRMFormGroup, DemandeXRMFormService } from './demande-xrm-form.service';

@Component({
  selector: 'jhi-demande-xrm-update',
  templateUrl: './demande-xrm-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DemandeXRMUpdateComponent implements OnInit {
  isSaving = false;
  demandeXRM: IDemandeXRM | null = null;

  pmEntreprisesCollection: IPmEntreprise[] = [];
  miseEnGestionsCollection: IMiseEnGestion[] = [];

  protected demandeXRMService = inject(DemandeXRMService);
  protected demandeXRMFormService = inject(DemandeXRMFormService);
  protected pmEntrepriseService = inject(PmEntrepriseService);
  protected miseEnGestionService = inject(MiseEnGestionService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DemandeXRMFormGroup = this.demandeXRMFormService.createDemandeXRMFormGroup();

  comparePmEntreprise = (o1: IPmEntreprise | null, o2: IPmEntreprise | null): boolean =>
    this.pmEntrepriseService.comparePmEntreprise(o1, o2);

  compareMiseEnGestion = (o1: IMiseEnGestion | null, o2: IMiseEnGestion | null): boolean =>
    this.miseEnGestionService.compareMiseEnGestion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandeXRM }) => {
      this.demandeXRM = demandeXRM;
      if (demandeXRM) {
        this.updateForm(demandeXRM);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandeXRM = this.demandeXRMFormService.getDemandeXRM(this.editForm);
    if (demandeXRM.id !== null) {
      this.subscribeToSaveResponse(this.demandeXRMService.update(demandeXRM));
    } else {
      this.subscribeToSaveResponse(this.demandeXRMService.create(demandeXRM));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeXRM>>): void {
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

  protected updateForm(demandeXRM: IDemandeXRM): void {
    this.demandeXRM = demandeXRM;
    this.demandeXRMFormService.resetForm(this.editForm, demandeXRM);

    this.pmEntreprisesCollection = this.pmEntrepriseService.addPmEntrepriseToCollectionIfMissing<IPmEntreprise>(
      this.pmEntreprisesCollection,
      demandeXRM.pmEntreprise,
    );
    this.miseEnGestionsCollection = this.miseEnGestionService.addMiseEnGestionToCollectionIfMissing<IMiseEnGestion>(
      this.miseEnGestionsCollection,
      demandeXRM.miseEnGestion,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pmEntrepriseService
      .query({ filter: 'demandexrm-is-null' })
      .pipe(map((res: HttpResponse<IPmEntreprise[]>) => res.body ?? []))
      .pipe(
        map((pmEntreprises: IPmEntreprise[]) =>
          this.pmEntrepriseService.addPmEntrepriseToCollectionIfMissing<IPmEntreprise>(pmEntreprises, this.demandeXRM?.pmEntreprise),
        ),
      )
      .subscribe((pmEntreprises: IPmEntreprise[]) => (this.pmEntreprisesCollection = pmEntreprises));

    this.miseEnGestionService
      .query({ filter: 'demandexrm-is-null' })
      .pipe(map((res: HttpResponse<IMiseEnGestion[]>) => res.body ?? []))
      .pipe(
        map((miseEnGestions: IMiseEnGestion[]) =>
          this.miseEnGestionService.addMiseEnGestionToCollectionIfMissing<IMiseEnGestion>(miseEnGestions, this.demandeXRM?.miseEnGestion),
        ),
      )
      .subscribe((miseEnGestions: IMiseEnGestion[]) => (this.miseEnGestionsCollection = miseEnGestions));
  }
}
