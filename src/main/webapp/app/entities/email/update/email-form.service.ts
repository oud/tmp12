import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmail, NewEmail } from '../email.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmail for edit and NewEmailFormGroupInput for create.
 */
type EmailFormGroupInput = IEmail | PartialWithRequiredKeyOf<NewEmail>;

type EmailFormDefaults = Pick<NewEmail, 'id'>;

type EmailFormGroupContent = {
  id: FormControl<IEmail['id'] | NewEmail['id']>;
  adresseEmail: FormControl<IEmail['adresseEmail']>;
  codeStatut: FormControl<IEmail['codeStatut']>;
  dateStatut: FormControl<IEmail['dateStatut']>;
  codeUsageEmail: FormControl<IEmail['codeUsageEmail']>;
  pmEntreprise: FormControl<IEmail['pmEntreprise']>;
  pmEtablissement: FormControl<IEmail['pmEtablissement']>;
};

export type EmailFormGroup = FormGroup<EmailFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmailFormService {
  createEmailFormGroup(email: EmailFormGroupInput = { id: null }): EmailFormGroup {
    const emailRawValue = {
      ...this.getFormDefaults(),
      ...email,
    };
    return new FormGroup<EmailFormGroupContent>({
      id: new FormControl(
        { value: emailRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      adresseEmail: new FormControl(emailRawValue.adresseEmail, {
        validators: [Validators.required],
      }),
      codeStatut: new FormControl(emailRawValue.codeStatut, {
        validators: [Validators.required],
      }),
      dateStatut: new FormControl(emailRawValue.dateStatut, {
        validators: [Validators.required],
      }),
      codeUsageEmail: new FormControl(emailRawValue.codeUsageEmail, {
        validators: [Validators.required],
      }),
      pmEntreprise: new FormControl(emailRawValue.pmEntreprise),
      pmEtablissement: new FormControl(emailRawValue.pmEtablissement),
    });
  }

  getEmail(form: EmailFormGroup): IEmail | NewEmail {
    return form.getRawValue() as IEmail | NewEmail;
  }

  resetForm(form: EmailFormGroup, email: EmailFormGroupInput): void {
    const emailRawValue = { ...this.getFormDefaults(), ...email };
    form.reset(
      {
        ...emailRawValue,
        id: { value: emailRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmailFormDefaults {
    return {
      id: null,
    };
  }
}
