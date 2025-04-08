import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITelephone, NewTelephone } from '../telephone.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITelephone for edit and NewTelephoneFormGroupInput for create.
 */
type TelephoneFormGroupInput = ITelephone | PartialWithRequiredKeyOf<NewTelephone>;

type TelephoneFormDefaults = Pick<NewTelephone, 'id'>;

type TelephoneFormGroupContent = {
  id: FormControl<ITelephone['id'] | NewTelephone['id']>;
  codePaysISO: FormControl<ITelephone['codePaysISO']>;
  codeTypeTelephone: FormControl<ITelephone['codeTypeTelephone']>;
  dateDerniereModification: FormControl<ITelephone['dateDerniereModification']>;
  codeIndicatifPays: FormControl<ITelephone['codeIndicatifPays']>;
  numeroTelephone: FormControl<ITelephone['numeroTelephone']>;
  codeStatut: FormControl<ITelephone['codeStatut']>;
  dateStatut: FormControl<ITelephone['dateStatut']>;
  codeUsageTelephone: FormControl<ITelephone['codeUsageTelephone']>;
  pmEntreprise: FormControl<ITelephone['pmEntreprise']>;
  pmEtablissement: FormControl<ITelephone['pmEtablissement']>;
};

export type TelephoneFormGroup = FormGroup<TelephoneFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TelephoneFormService {
  createTelephoneFormGroup(telephone: TelephoneFormGroupInput = { id: null }): TelephoneFormGroup {
    const telephoneRawValue = {
      ...this.getFormDefaults(),
      ...telephone,
    };
    return new FormGroup<TelephoneFormGroupContent>({
      id: new FormControl(
        { value: telephoneRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codePaysISO: new FormControl(telephoneRawValue.codePaysISO, {
        validators: [Validators.required],
      }),
      codeTypeTelephone: new FormControl(telephoneRawValue.codeTypeTelephone, {
        validators: [Validators.required],
      }),
      dateDerniereModification: new FormControl(telephoneRawValue.dateDerniereModification, {
        validators: [Validators.required],
      }),
      codeIndicatifPays: new FormControl(telephoneRawValue.codeIndicatifPays, {
        validators: [Validators.required],
      }),
      numeroTelephone: new FormControl(telephoneRawValue.numeroTelephone, {
        validators: [Validators.required],
      }),
      codeStatut: new FormControl(telephoneRawValue.codeStatut, {
        validators: [Validators.required],
      }),
      dateStatut: new FormControl(telephoneRawValue.dateStatut, {
        validators: [Validators.required],
      }),
      codeUsageTelephone: new FormControl(telephoneRawValue.codeUsageTelephone, {
        validators: [Validators.required],
      }),
      pmEntreprise: new FormControl(telephoneRawValue.pmEntreprise),
      pmEtablissement: new FormControl(telephoneRawValue.pmEtablissement),
    });
  }

  getTelephone(form: TelephoneFormGroup): ITelephone | NewTelephone {
    return form.getRawValue() as ITelephone | NewTelephone;
  }

  resetForm(form: TelephoneFormGroup, telephone: TelephoneFormGroupInput): void {
    const telephoneRawValue = { ...this.getFormDefaults(), ...telephone };
    form.reset(
      {
        ...telephoneRawValue,
        id: { value: telephoneRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TelephoneFormDefaults {
    return {
      id: null,
    };
  }
}
