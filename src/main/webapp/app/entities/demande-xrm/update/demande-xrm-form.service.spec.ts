import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../demande-xrm.test-samples';

import { DemandeXRMFormService } from './demande-xrm-form.service';

describe('DemandeXRM Form Service', () => {
  let service: DemandeXRMFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemandeXRMFormService);
  });

  describe('Service methods', () => {
    describe('createDemandeXRMFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemandeXRMFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pmEntreprise: expect.any(Object),
            miseEnGestion: expect.any(Object),
          }),
        );
      });

      it('passing IDemandeXRM should create a new form with FormGroup', () => {
        const formGroup = service.createDemandeXRMFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pmEntreprise: expect.any(Object),
            miseEnGestion: expect.any(Object),
          }),
        );
      });
    });

    describe('getDemandeXRM', () => {
      it('should return NewDemandeXRM for default DemandeXRM initial value', () => {
        const formGroup = service.createDemandeXRMFormGroup(sampleWithNewData);

        const demandeXRM = service.getDemandeXRM(formGroup) as any;

        expect(demandeXRM).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemandeXRM for empty DemandeXRM initial value', () => {
        const formGroup = service.createDemandeXRMFormGroup();

        const demandeXRM = service.getDemandeXRM(formGroup) as any;

        expect(demandeXRM).toMatchObject({});
      });

      it('should return IDemandeXRM', () => {
        const formGroup = service.createDemandeXRMFormGroup(sampleWithRequiredData);

        const demandeXRM = service.getDemandeXRM(formGroup) as any;

        expect(demandeXRM).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemandeXRM should not enable id FormControl', () => {
        const formGroup = service.createDemandeXRMFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemandeXRM should disable id FormControl', () => {
        const formGroup = service.createDemandeXRMFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
