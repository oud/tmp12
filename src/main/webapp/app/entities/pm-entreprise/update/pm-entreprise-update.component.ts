import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPmEntreprise } from '../pm-entreprise.model';
import { PmEntrepriseService } from '../service/pm-entreprise.service';
import { PmEntrepriseFormGroup, PmEntrepriseFormService } from './pm-entreprise-form.service';

@Component({
  selector: 'jhi-pm-entreprise-update',
  templateUrl: './pm-entreprise-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PmEntrepriseUpdateComponent implements OnInit {
  isSaving = false;
  pmEntreprise: IPmEntreprise | null = null;

  protected pmEntrepriseService = inject(PmEntrepriseService);
  protected pmEntrepriseFormService = inject(PmEntrepriseFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PmEntrepriseFormGroup = this.pmEntrepriseFormService.createPmEntrepriseFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pmEntreprise }) => {
      this.pmEntreprise = pmEntreprise;
      if (pmEntreprise) {
        this.updateForm(pmEntreprise);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pmEntreprise = this.pmEntrepriseFormService.getPmEntreprise(this.editForm);
    if (pmEntreprise.id !== null) {
      this.subscribeToSaveResponse(this.pmEntrepriseService.update(pmEntreprise));
    } else {
      this.subscribeToSaveResponse(this.pmEntrepriseService.create(pmEntreprise));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPmEntreprise>>): void {
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

  protected updateForm(pmEntreprise: IPmEntreprise): void {
    this.pmEntreprise = pmEntreprise;
    this.pmEntrepriseFormService.resetForm(this.editForm, pmEntreprise);
  }
}
