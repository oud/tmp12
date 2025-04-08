import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPmEtablissement, NewPmEtablissement } from '../pm-etablissement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPmEtablissement for edit and NewPmEtablissementFormGroupInput for create.
 */
type PmEtablissementFormGroupInput = IPmEtablissement | PartialWithRequiredKeyOf<NewPmEtablissement>;

type PmEtablissementFormDefaults = Pick<NewPmEtablissement, 'id'>;

type PmEtablissementFormGroupContent = {
  id: FormControl<IPmEtablissement['id'] | NewPmEtablissement['id']>;
  idEtablissementRPG: FormControl<IPmEtablissement['idEtablissementRPG']>;
  codePartenaireDistributeur: FormControl<IPmEtablissement['codePartenaireDistributeur']>;
  numeroSiretSiege: FormControl<IPmEtablissement['numeroSiretSiege']>;
  codeEtat: FormControl<IPmEtablissement['codeEtat']>;
  libelleEtat: FormControl<IPmEtablissement['libelleEtat']>;
  codeCategoriePersonne: FormControl<IPmEtablissement['codeCategoriePersonne']>;
  libelleCategoriePersonne: FormControl<IPmEtablissement['libelleCategoriePersonne']>;
  codeType: FormControl<IPmEtablissement['codeType']>;
  dateCreationJuridique: FormControl<IPmEtablissement['dateCreationJuridique']>;
  dateEtat: FormControl<IPmEtablissement['dateEtat']>;
  dateFermetureJuridique: FormControl<IPmEtablissement['dateFermetureJuridique']>;
  codeIDCC: FormControl<IPmEtablissement['codeIDCC']>;
  codeTrancheEffectif: FormControl<IPmEtablissement['codeTrancheEffectif']>;
  libelleTrancheEffectif: FormControl<IPmEtablissement['libelleTrancheEffectif']>;
  dateCessationActivite: FormControl<IPmEtablissement['dateCessationActivite']>;
  dateEffectifOfficiel: FormControl<IPmEtablissement['dateEffectifOfficiel']>;
  effectifOfficiel: FormControl<IPmEtablissement['effectifOfficiel']>;
  codeMotifCessationActivite: FormControl<IPmEtablissement['codeMotifCessationActivite']>;
  siret: FormControl<IPmEtablissement['siret']>;
  codeTypeEtablissement: FormControl<IPmEtablissement['codeTypeEtablissement']>;
  libelleEnseigne: FormControl<IPmEtablissement['libelleEnseigne']>;
  codeNIC: FormControl<IPmEtablissement['codeNIC']>;
  demandeXRM: FormControl<IPmEtablissement['demandeXRM']>;
};

export type PmEtablissementFormGroup = FormGroup<PmEtablissementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PmEtablissementFormService {
  createPmEtablissementFormGroup(pmEtablissement: PmEtablissementFormGroupInput = { id: null }): PmEtablissementFormGroup {
    const pmEtablissementRawValue = {
      ...this.getFormDefaults(),
      ...pmEtablissement,
    };
    return new FormGroup<PmEtablissementFormGroupContent>({
      id: new FormControl(
        { value: pmEtablissementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      idEtablissementRPG: new FormControl(pmEtablissementRawValue.idEtablissementRPG, {
        validators: [Validators.required],
      }),
      codePartenaireDistributeur: new FormControl(pmEtablissementRawValue.codePartenaireDistributeur, {
        validators: [Validators.required],
      }),
      numeroSiretSiege: new FormControl(pmEtablissementRawValue.numeroSiretSiege, {
        validators: [Validators.required],
      }),
      codeEtat: new FormControl(pmEtablissementRawValue.codeEtat, {
        validators: [Validators.required],
      }),
      libelleEtat: new FormControl(pmEtablissementRawValue.libelleEtat, {
        validators: [Validators.required],
      }),
      codeCategoriePersonne: new FormControl(pmEtablissementRawValue.codeCategoriePersonne, {
        validators: [Validators.required],
      }),
      libelleCategoriePersonne: new FormControl(pmEtablissementRawValue.libelleCategoriePersonne, {
        validators: [Validators.required],
      }),
      codeType: new FormControl(pmEtablissementRawValue.codeType, {
        validators: [Validators.required],
      }),
      dateCreationJuridique: new FormControl(pmEtablissementRawValue.dateCreationJuridique, {
        validators: [Validators.required],
      }),
      dateEtat: new FormControl(pmEtablissementRawValue.dateEtat, {
        validators: [Validators.required],
      }),
      dateFermetureJuridique: new FormControl(pmEtablissementRawValue.dateFermetureJuridique, {
        validators: [Validators.required],
      }),
      codeIDCC: new FormControl(pmEtablissementRawValue.codeIDCC, {
        validators: [Validators.required],
      }),
      codeTrancheEffectif: new FormControl(pmEtablissementRawValue.codeTrancheEffectif, {
        validators: [Validators.required],
      }),
      libelleTrancheEffectif: new FormControl(pmEtablissementRawValue.libelleTrancheEffectif, {
        validators: [Validators.required],
      }),
      dateCessationActivite: new FormControl(pmEtablissementRawValue.dateCessationActivite, {
        validators: [Validators.required],
      }),
      dateEffectifOfficiel: new FormControl(pmEtablissementRawValue.dateEffectifOfficiel, {
        validators: [Validators.required],
      }),
      effectifOfficiel: new FormControl(pmEtablissementRawValue.effectifOfficiel, {
        validators: [Validators.required],
      }),
      codeMotifCessationActivite: new FormControl(pmEtablissementRawValue.codeMotifCessationActivite, {
        validators: [Validators.required],
      }),
      siret: new FormControl(pmEtablissementRawValue.siret, {
        validators: [Validators.required],
      }),
      codeTypeEtablissement: new FormControl(pmEtablissementRawValue.codeTypeEtablissement, {
        validators: [Validators.required],
      }),
      libelleEnseigne: new FormControl(pmEtablissementRawValue.libelleEnseigne, {
        validators: [Validators.required],
      }),
      codeNIC: new FormControl(pmEtablissementRawValue.codeNIC, {
        validators: [Validators.required],
      }),
      demandeXRM: new FormControl(pmEtablissementRawValue.demandeXRM),
    });
  }

  getPmEtablissement(form: PmEtablissementFormGroup): IPmEtablissement | NewPmEtablissement {
    return form.getRawValue() as IPmEtablissement | NewPmEtablissement;
  }

  resetForm(form: PmEtablissementFormGroup, pmEtablissement: PmEtablissementFormGroupInput): void {
    const pmEtablissementRawValue = { ...this.getFormDefaults(), ...pmEtablissement };
    form.reset(
      {
        ...pmEtablissementRawValue,
        id: { value: pmEtablissementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PmEtablissementFormDefaults {
    return {
      id: null,
    };
  }
}
