import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../telephone.test-samples';

import { TelephoneFormService } from './telephone-form.service';

describe('Telephone Form Service', () => {
  let service: TelephoneFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TelephoneFormService);
  });

  describe('Service methods', () => {
    describe('createTelephoneFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTelephoneFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codePaysISO: expect.any(Object),
            codeTypeTelephone: expect.any(Object),
            dateDerniereModification: expect.any(Object),
            codeIndicatifPays: expect.any(Object),
            numeroTelephone: expect.any(Object),
            codeStatut: expect.any(Object),
            dateStatut: expect.any(Object),
            codeUsageTelephone: expect.any(Object),
            pmEntreprise: expect.any(Object),
            pmEtablissement: expect.any(Object),
          }),
        );
      });

      it('passing ITelephone should create a new form with FormGroup', () => {
        const formGroup = service.createTelephoneFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codePaysISO: expect.any(Object),
            codeTypeTelephone: expect.any(Object),
            dateDerniereModification: expect.any(Object),
            codeIndicatifPays: expect.any(Object),
            numeroTelephone: expect.any(Object),
            codeStatut: expect.any(Object),
            dateStatut: expect.any(Object),
            codeUsageTelephone: expect.any(Object),
            pmEntreprise: expect.any(Object),
            pmEtablissement: expect.any(Object),
          }),
        );
      });
    });

    describe('getTelephone', () => {
      it('should return NewTelephone for default Telephone initial value', () => {
        const formGroup = service.createTelephoneFormGroup(sampleWithNewData);

        const telephone = service.getTelephone(formGroup) as any;

        expect(telephone).toMatchObject(sampleWithNewData);
      });

      it('should return NewTelephone for empty Telephone initial value', () => {
        const formGroup = service.createTelephoneFormGroup();

        const telephone = service.getTelephone(formGroup) as any;

        expect(telephone).toMatchObject({});
      });

      it('should return ITelephone', () => {
        const formGroup = service.createTelephoneFormGroup(sampleWithRequiredData);

        const telephone = service.getTelephone(formGroup) as any;

        expect(telephone).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITelephone should not enable id FormControl', () => {
        const formGroup = service.createTelephoneFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTelephone should disable id FormControl', () => {
        const formGroup = service.createTelephoneFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
