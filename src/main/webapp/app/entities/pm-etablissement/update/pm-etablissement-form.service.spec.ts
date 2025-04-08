import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../pm-etablissement.test-samples';

import { PmEtablissementFormService } from './pm-etablissement-form.service';

describe('PmEtablissement Form Service', () => {
  let service: PmEtablissementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PmEtablissementFormService);
  });

  describe('Service methods', () => {
    describe('createPmEtablissementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPmEtablissementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idEtablissementRPG: expect.any(Object),
            codePartenaireDistributeur: expect.any(Object),
            numeroSiretSiege: expect.any(Object),
            codeEtat: expect.any(Object),
            libelleEtat: expect.any(Object),
            codeCategoriePersonne: expect.any(Object),
            libelleCategoriePersonne: expect.any(Object),
            codeType: expect.any(Object),
            dateCreationJuridique: expect.any(Object),
            dateEtat: expect.any(Object),
            dateFermetureJuridique: expect.any(Object),
            codeIDCC: expect.any(Object),
            codeTrancheEffectif: expect.any(Object),
            libelleTrancheEffectif: expect.any(Object),
            dateCessationActivite: expect.any(Object),
            dateEffectifOfficiel: expect.any(Object),
            effectifOfficiel: expect.any(Object),
            codeMotifCessationActivite: expect.any(Object),
            siret: expect.any(Object),
            codeTypeEtablissement: expect.any(Object),
            libelleEnseigne: expect.any(Object),
            codeNIC: expect.any(Object),
            demandeXRM: expect.any(Object),
          }),
        );
      });

      it('passing IPmEtablissement should create a new form with FormGroup', () => {
        const formGroup = service.createPmEtablissementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idEtablissementRPG: expect.any(Object),
            codePartenaireDistributeur: expect.any(Object),
            numeroSiretSiege: expect.any(Object),
            codeEtat: expect.any(Object),
            libelleEtat: expect.any(Object),
            codeCategoriePersonne: expect.any(Object),
            libelleCategoriePersonne: expect.any(Object),
            codeType: expect.any(Object),
            dateCreationJuridique: expect.any(Object),
            dateEtat: expect.any(Object),
            dateFermetureJuridique: expect.any(Object),
            codeIDCC: expect.any(Object),
            codeTrancheEffectif: expect.any(Object),
            libelleTrancheEffectif: expect.any(Object),
            dateCessationActivite: expect.any(Object),
            dateEffectifOfficiel: expect.any(Object),
            effectifOfficiel: expect.any(Object),
            codeMotifCessationActivite: expect.any(Object),
            siret: expect.any(Object),
            codeTypeEtablissement: expect.any(Object),
            libelleEnseigne: expect.any(Object),
            codeNIC: expect.any(Object),
            demandeXRM: expect.any(Object),
          }),
        );
      });
    });

    describe('getPmEtablissement', () => {
      it('should return NewPmEtablissement for default PmEtablissement initial value', () => {
        const formGroup = service.createPmEtablissementFormGroup(sampleWithNewData);

        const pmEtablissement = service.getPmEtablissement(formGroup) as any;

        expect(pmEtablissement).toMatchObject(sampleWithNewData);
      });

      it('should return NewPmEtablissement for empty PmEtablissement initial value', () => {
        const formGroup = service.createPmEtablissementFormGroup();

        const pmEtablissement = service.getPmEtablissement(formGroup) as any;

        expect(pmEtablissement).toMatchObject({});
      });

      it('should return IPmEtablissement', () => {
        const formGroup = service.createPmEtablissementFormGroup(sampleWithRequiredData);

        const pmEtablissement = service.getPmEtablissement(formGroup) as any;

        expect(pmEtablissement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPmEtablissement should not enable id FormControl', () => {
        const formGroup = service.createPmEtablissementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPmEtablissement should disable id FormControl', () => {
        const formGroup = service.createPmEtablissementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
