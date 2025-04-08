import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPmEntreprise, NewPmEntreprise } from '../pm-entreprise.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPmEntreprise for edit and NewPmEntrepriseFormGroupInput for create.
 */
type PmEntrepriseFormGroupInput = IPmEntreprise | PartialWithRequiredKeyOf<NewPmEntreprise>;

type PmEntrepriseFormDefaults = Pick<NewPmEntreprise, 'id'>;

type PmEntrepriseFormGroupContent = {
  id: FormControl<IPmEntreprise['id'] | NewPmEntreprise['id']>;
  idEntrepriseRPG: FormControl<IPmEntreprise['idEntrepriseRPG']>;
  codePartenaireDistributeur: FormControl<IPmEntreprise['codePartenaireDistributeur']>;
  numeroSiretSiege: FormControl<IPmEntreprise['numeroSiretSiege']>;
  codeEtat: FormControl<IPmEntreprise['codeEtat']>;
  libelleEtat: FormControl<IPmEntreprise['libelleEtat']>;
  codeCategoriePersonne: FormControl<IPmEntreprise['codeCategoriePersonne']>;
  libelleCategoriePersonne: FormControl<IPmEntreprise['libelleCategoriePersonne']>;
  codeType: FormControl<IPmEntreprise['codeType']>;
  dateCreationJuridique: FormControl<IPmEntreprise['dateCreationJuridique']>;
  dateEtat: FormControl<IPmEntreprise['dateEtat']>;
  dateFermetureJuridique: FormControl<IPmEntreprise['dateFermetureJuridique']>;
  codeTrancheEffectif: FormControl<IPmEntreprise['codeTrancheEffectif']>;
  libelleTrancheEffectif: FormControl<IPmEntreprise['libelleTrancheEffectif']>;
  dateCessationActivite: FormControl<IPmEntreprise['dateCessationActivite']>;
  dateEffectifOfficiel: FormControl<IPmEntreprise['dateEffectifOfficiel']>;
  effectifOfficiel: FormControl<IPmEntreprise['effectifOfficiel']>;
  codeMotifCessationActivite: FormControl<IPmEntreprise['codeMotifCessationActivite']>;
  siren: FormControl<IPmEntreprise['siren']>;
  codeFormeJuridique: FormControl<IPmEntreprise['codeFormeJuridique']>;
  raisonSociale: FormControl<IPmEntreprise['raisonSociale']>;
  codeCategorieJuridique: FormControl<IPmEntreprise['codeCategorieJuridique']>;
  codeIDCC: FormControl<IPmEntreprise['codeIDCC']>;
  codeAPE: FormControl<IPmEntreprise['codeAPE']>;
};

export type PmEntrepriseFormGroup = FormGroup<PmEntrepriseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PmEntrepriseFormService {
  createPmEntrepriseFormGroup(pmEntreprise: PmEntrepriseFormGroupInput = { id: null }): PmEntrepriseFormGroup {
    const pmEntrepriseRawValue = {
      ...this.getFormDefaults(),
      ...pmEntreprise,
    };
    return new FormGroup<PmEntrepriseFormGroupContent>({
      id: new FormControl(
        { value: pmEntrepriseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      idEntrepriseRPG: new FormControl(pmEntrepriseRawValue.idEntrepriseRPG, {
        validators: [Validators.required],
      }),
      codePartenaireDistributeur: new FormControl(pmEntrepriseRawValue.codePartenaireDistributeur, {
        validators: [Validators.required],
      }),
      numeroSiretSiege: new FormControl(pmEntrepriseRawValue.numeroSiretSiege, {
        validators: [Validators.required],
      }),
      codeEtat: new FormControl(pmEntrepriseRawValue.codeEtat, {
        validators: [Validators.required],
      }),
      libelleEtat: new FormControl(pmEntrepriseRawValue.libelleEtat, {
        validators: [Validators.required],
      }),
      codeCategoriePersonne: new FormControl(pmEntrepriseRawValue.codeCategoriePersonne, {
        validators: [Validators.required],
      }),
      libelleCategoriePersonne: new FormControl(pmEntrepriseRawValue.libelleCategoriePersonne, {
        validators: [Validators.required],
      }),
      codeType: new FormControl(pmEntrepriseRawValue.codeType, {
        validators: [Validators.required],
      }),
      dateCreationJuridique: new FormControl(pmEntrepriseRawValue.dateCreationJuridique, {
        validators: [Validators.required],
      }),
      dateEtat: new FormControl(pmEntrepriseRawValue.dateEtat, {
        validators: [Validators.required],
      }),
      dateFermetureJuridique: new FormControl(pmEntrepriseRawValue.dateFermetureJuridique, {
        validators: [Validators.required],
      }),
      codeTrancheEffectif: new FormControl(pmEntrepriseRawValue.codeTrancheEffectif, {
        validators: [Validators.required],
      }),
      libelleTrancheEffectif: new FormControl(pmEntrepriseRawValue.libelleTrancheEffectif, {
        validators: [Validators.required],
      }),
      dateCessationActivite: new FormControl(pmEntrepriseRawValue.dateCessationActivite, {
        validators: [Validators.required],
      }),
      dateEffectifOfficiel: new FormControl(pmEntrepriseRawValue.dateEffectifOfficiel, {
        validators: [Validators.required],
      }),
      effectifOfficiel: new FormControl(pmEntrepriseRawValue.effectifOfficiel, {
        validators: [Validators.required],
      }),
      codeMotifCessationActivite: new FormControl(pmEntrepriseRawValue.codeMotifCessationActivite, {
        validators: [Validators.required],
      }),
      siren: new FormControl(pmEntrepriseRawValue.siren, {
        validators: [Validators.required],
      }),
      codeFormeJuridique: new FormControl(pmEntrepriseRawValue.codeFormeJuridique, {
        validators: [Validators.required],
      }),
      raisonSociale: new FormControl(pmEntrepriseRawValue.raisonSociale, {
        validators: [Validators.required],
      }),
      codeCategorieJuridique: new FormControl(pmEntrepriseRawValue.codeCategorieJuridique, {
        validators: [Validators.required],
      }),
      codeIDCC: new FormControl(pmEntrepriseRawValue.codeIDCC, {
        validators: [Validators.required],
      }),
      codeAPE: new FormControl(pmEntrepriseRawValue.codeAPE, {
        validators: [Validators.required],
      }),
    });
  }

  getPmEntreprise(form: PmEntrepriseFormGroup): IPmEntreprise | NewPmEntreprise {
    return form.getRawValue() as IPmEntreprise | NewPmEntreprise;
  }

  resetForm(form: PmEntrepriseFormGroup, pmEntreprise: PmEntrepriseFormGroupInput): void {
    const pmEntrepriseRawValue = { ...this.getFormDefaults(), ...pmEntreprise };
    form.reset(
      {
        ...pmEntrepriseRawValue,
        id: { value: pmEntrepriseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PmEntrepriseFormDefaults {
    return {
      id: null,
    };
  }
}
