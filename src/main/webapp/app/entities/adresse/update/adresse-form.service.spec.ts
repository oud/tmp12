import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../adresse.test-samples';

import { AdresseFormService } from './adresse-form.service';

describe('Adresse Form Service', () => {
  let service: AdresseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdresseFormService);
  });

  describe('Service methods', () => {
    describe('createAdresseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAdresseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codePaysISO: expect.any(Object),
            codePostal: expect.any(Object),
            dateDerniereModification: expect.any(Object),
            libelleCommune: expect.any(Object),
            ligne1: expect.any(Object),
            ligne2: expect.any(Object),
            ligne3: expect.any(Object),
            ligne4: expect.any(Object),
            ligne5: expect.any(Object),
            ligne6: expect.any(Object),
            ligne7: expect.any(Object),
            nombreCourriersPND: expect.any(Object),
            codeUsageAdresse: expect.any(Object),
            pmEntreprise: expect.any(Object),
            pmEtablissement: expect.any(Object),
          }),
        );
      });

      it('passing IAdresse should create a new form with FormGroup', () => {
        const formGroup = service.createAdresseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codePaysISO: expect.any(Object),
            codePostal: expect.any(Object),
            dateDerniereModification: expect.any(Object),
            libelleCommune: expect.any(Object),
            ligne1: expect.any(Object),
            ligne2: expect.any(Object),
            ligne3: expect.any(Object),
            ligne4: expect.any(Object),
            ligne5: expect.any(Object),
            ligne6: expect.any(Object),
            ligne7: expect.any(Object),
            nombreCourriersPND: expect.any(Object),
            codeUsageAdresse: expect.any(Object),
            pmEntreprise: expect.any(Object),
            pmEtablissement: expect.any(Object),
          }),
        );
      });
    });

    describe('getAdresse', () => {
      it('should return NewAdresse for default Adresse initial value', () => {
        const formGroup = service.createAdresseFormGroup(sampleWithNewData);

        const adresse = service.getAdresse(formGroup) as any;

        expect(adresse).toMatchObject(sampleWithNewData);
      });

      it('should return NewAdresse for empty Adresse initial value', () => {
        const formGroup = service.createAdresseFormGroup();

        const adresse = service.getAdresse(formGroup) as any;

        expect(adresse).toMatchObject({});
      });

      it('should return IAdresse', () => {
        const formGroup = service.createAdresseFormGroup(sampleWithRequiredData);

        const adresse = service.getAdresse(formGroup) as any;

        expect(adresse).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAdresse should not enable id FormControl', () => {
        const formGroup = service.createAdresseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAdresse should disable id FormControl', () => {
        const formGroup = service.createAdresseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
