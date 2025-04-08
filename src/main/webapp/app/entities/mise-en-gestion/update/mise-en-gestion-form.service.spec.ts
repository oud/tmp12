import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../mise-en-gestion.test-samples';

import { MiseEnGestionFormService } from './mise-en-gestion-form.service';

describe('MiseEnGestion Form Service', () => {
  let service: MiseEnGestionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MiseEnGestionFormService);
  });

  describe('Service methods', () => {
    describe('createMiseEnGestionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMiseEnGestionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            typeMEG: expect.any(Object),
            codeOffre: expect.any(Object),
            dateEffet: expect.any(Object),
          }),
        );
      });

      it('passing IMiseEnGestion should create a new form with FormGroup', () => {
        const formGroup = service.createMiseEnGestionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            typeMEG: expect.any(Object),
            codeOffre: expect.any(Object),
            dateEffet: expect.any(Object),
          }),
        );
      });
    });

    describe('getMiseEnGestion', () => {
      it('should return NewMiseEnGestion for default MiseEnGestion initial value', () => {
        const formGroup = service.createMiseEnGestionFormGroup(sampleWithNewData);

        const miseEnGestion = service.getMiseEnGestion(formGroup) as any;

        expect(miseEnGestion).toMatchObject(sampleWithNewData);
      });

      it('should return NewMiseEnGestion for empty MiseEnGestion initial value', () => {
        const formGroup = service.createMiseEnGestionFormGroup();

        const miseEnGestion = service.getMiseEnGestion(formGroup) as any;

        expect(miseEnGestion).toMatchObject({});
      });

      it('should return IMiseEnGestion', () => {
        const formGroup = service.createMiseEnGestionFormGroup(sampleWithRequiredData);

        const miseEnGestion = service.getMiseEnGestion(formGroup) as any;

        expect(miseEnGestion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMiseEnGestion should not enable id FormControl', () => {
        const formGroup = service.createMiseEnGestionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMiseEnGestion should disable id FormControl', () => {
        const formGroup = service.createMiseEnGestionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
