import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMiseEnGestion } from '../mise-en-gestion.model';
import { MiseEnGestionService } from '../service/mise-en-gestion.service';
import { MiseEnGestionFormGroup, MiseEnGestionFormService } from './mise-en-gestion-form.service';

@Component({
  selector: 'jhi-mise-en-gestion-update',
  templateUrl: './mise-en-gestion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MiseEnGestionUpdateComponent implements OnInit {
  isSaving = false;
  miseEnGestion: IMiseEnGestion | null = null;

  protected miseEnGestionService = inject(MiseEnGestionService);
  protected miseEnGestionFormService = inject(MiseEnGestionFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MiseEnGestionFormGroup = this.miseEnGestionFormService.createMiseEnGestionFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ miseEnGestion }) => {
      this.miseEnGestion = miseEnGestion;
      if (miseEnGestion) {
        this.updateForm(miseEnGestion);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const miseEnGestion = this.miseEnGestionFormService.getMiseEnGestion(this.editForm);
    if (miseEnGestion.id !== null) {
      this.subscribeToSaveResponse(this.miseEnGestionService.update(miseEnGestion));
    } else {
      this.subscribeToSaveResponse(this.miseEnGestionService.create(miseEnGestion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMiseEnGestion>>): void {
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

  protected updateForm(miseEnGestion: IMiseEnGestion): void {
    this.miseEnGestion = miseEnGestion;
    this.miseEnGestionFormService.resetForm(this.editForm, miseEnGestion);
  }
}
