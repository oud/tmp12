import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../pm-entreprise.test-samples';

import { PmEntrepriseFormService } from './pm-entreprise-form.service';

describe('PmEntreprise Form Service', () => {
  let service: PmEntrepriseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PmEntrepriseFormService);
  });

  describe('Service methods', () => {
    describe('createPmEntrepriseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPmEntrepriseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idEntrepriseRPG: expect.any(Object),
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
            codeTrancheEffectif: expect.any(Object),
            libelleTrancheEffectif: expect.any(Object),
            dateCessationActivite: expect.any(Object),
            dateEffectifOfficiel: expect.any(Object),
            effectifOfficiel: expect.any(Object),
            codeMotifCessationActivite: expect.any(Object),
            siren: expect.any(Object),
            codeFormeJuridique: expect.any(Object),
            raisonSociale: expect.any(Object),
            codeCategorieJuridique: expect.any(Object),
            codeIDCC: expect.any(Object),
            codeAPE: expect.any(Object),
          }),
        );
      });

      it('passing IPmEntreprise should create a new form with FormGroup', () => {
        const formGroup = service.createPmEntrepriseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idEntrepriseRPG: expect.any(Object),
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
            codeTrancheEffectif: expect.any(Object),
            libelleTrancheEffectif: expect.any(Object),
            dateCessationActivite: expect.any(Object),
            dateEffectifOfficiel: expect.any(Object),
            effectifOfficiel: expect.any(Object),
            codeMotifCessationActivite: expect.any(Object),
            siren: expect.any(Object),
            codeFormeJuridique: expect.any(Object),
            raisonSociale: expect.any(Object),
            codeCategorieJuridique: expect.any(Object),
            codeIDCC: expect.any(Object),
            codeAPE: expect.any(Object),
          }),
        );
      });
    });

    describe('getPmEntreprise', () => {
      it('should return NewPmEntreprise for default PmEntreprise initial value', () => {
        const formGroup = service.createPmEntrepriseFormGroup(sampleWithNewData);

        const pmEntreprise = service.getPmEntreprise(formGroup) as any;

        expect(pmEntreprise).toMatchObject(sampleWithNewData);
      });

      it('should return NewPmEntreprise for empty PmEntreprise initial value', () => {
        const formGroup = service.createPmEntrepriseFormGroup();

        const pmEntreprise = service.getPmEntreprise(formGroup) as any;

        expect(pmEntreprise).toMatchObject({});
      });

      it('should return IPmEntreprise', () => {
        const formGroup = service.createPmEntrepriseFormGroup(sampleWithRequiredData);

        const pmEntreprise = service.getPmEntreprise(formGroup) as any;

        expect(pmEntreprise).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPmEntreprise should not enable id FormControl', () => {
        const formGroup = service.createPmEntrepriseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPmEntreprise should disable id FormControl', () => {
        const formGroup = service.createPmEntrepriseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
