import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMiseEnGestion, NewMiseEnGestion } from '../mise-en-gestion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMiseEnGestion for edit and NewMiseEnGestionFormGroupInput for create.
 */
type MiseEnGestionFormGroupInput = IMiseEnGestion | PartialWithRequiredKeyOf<NewMiseEnGestion>;

type MiseEnGestionFormDefaults = Pick<NewMiseEnGestion, 'id'>;

type MiseEnGestionFormGroupContent = {
  id: FormControl<IMiseEnGestion['id'] | NewMiseEnGestion['id']>;
  typeMEG: FormControl<IMiseEnGestion['typeMEG']>;
  codeOffre: FormControl<IMiseEnGestion['codeOffre']>;
  dateEffet: FormControl<IMiseEnGestion['dateEffet']>;
};

export type MiseEnGestionFormGroup = FormGroup<MiseEnGestionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MiseEnGestionFormService {
  createMiseEnGestionFormGroup(miseEnGestion: MiseEnGestionFormGroupInput = { id: null }): MiseEnGestionFormGroup {
    const miseEnGestionRawValue = {
      ...this.getFormDefaults(),
      ...miseEnGestion,
    };
    return new FormGroup<MiseEnGestionFormGroupContent>({
      id: new FormControl(
        { value: miseEnGestionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      typeMEG: new FormControl(miseEnGestionRawValue.typeMEG, {
        validators: [Validators.required],
      }),
      codeOffre: new FormControl(miseEnGestionRawValue.codeOffre, {
        validators: [Validators.required],
      }),
      dateEffet: new FormControl(miseEnGestionRawValue.dateEffet, {
        validators: [Validators.required],
      }),
    });
  }

  getMiseEnGestion(form: MiseEnGestionFormGroup): IMiseEnGestion | NewMiseEnGestion {
    return form.getRawValue() as IMiseEnGestion | NewMiseEnGestion;
  }

  resetForm(form: MiseEnGestionFormGroup, miseEnGestion: MiseEnGestionFormGroupInput): void {
    const miseEnGestionRawValue = { ...this.getFormDefaults(), ...miseEnGestion };
    form.reset(
      {
        ...miseEnGestionRawValue,
        id: { value: miseEnGestionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MiseEnGestionFormDefaults {
    return {
      id: null,
    };
  }
}
