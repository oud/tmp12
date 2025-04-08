import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPmEntreprise } from 'app/entities/pm-entreprise/pm-entreprise.model';
import { PmEntrepriseService } from 'app/entities/pm-entreprise/service/pm-entreprise.service';
import { IMiseEnGestion } from 'app/entities/mise-en-gestion/mise-en-gestion.model';
import { MiseEnGestionService } from 'app/entities/mise-en-gestion/service/mise-en-gestion.service';
import { IDemandeXRM } from '../demande-xrm.model';
import { DemandeXRMService } from '../service/demande-xrm.service';
import { DemandeXRMFormService } from './demande-xrm-form.service';

import { DemandeXRMUpdateComponent } from './demande-xrm-update.component';

describe('DemandeXRM Management Update Component', () => {
  let comp: DemandeXRMUpdateComponent;
  let fixture: ComponentFixture<DemandeXRMUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let demandeXRMFormService: DemandeXRMFormService;
  let demandeXRMService: DemandeXRMService;
  let pmEntrepriseService: PmEntrepriseService;
  let miseEnGestionService: MiseEnGestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DemandeXRMUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DemandeXRMUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DemandeXRMUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    demandeXRMFormService = TestBed.inject(DemandeXRMFormService);
    demandeXRMService = TestBed.inject(DemandeXRMService);
    pmEntrepriseService = TestBed.inject(PmEntrepriseService);
    miseEnGestionService = TestBed.inject(MiseEnGestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call pmEntreprise query and add missing value', () => {
      const demandeXRM: IDemandeXRM = { id: 11627 };
      const pmEntreprise: IPmEntreprise = { id: 16964 };
      demandeXRM.pmEntreprise = pmEntreprise;

      const pmEntrepriseCollection: IPmEntreprise[] = [{ id: 16964 }];
      jest.spyOn(pmEntrepriseService, 'query').mockReturnValue(of(new HttpResponse({ body: pmEntrepriseCollection })));
      const expectedCollection: IPmEntreprise[] = [pmEntreprise, ...pmEntrepriseCollection];
      jest.spyOn(pmEntrepriseService, 'addPmEntrepriseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeXRM });
      comp.ngOnInit();

      expect(pmEntrepriseService.query).toHaveBeenCalled();
      expect(pmEntrepriseService.addPmEntrepriseToCollectionIfMissing).toHaveBeenCalledWith(pmEntrepriseCollection, pmEntreprise);
      expect(comp.pmEntreprisesCollection).toEqual(expectedCollection);
    });

    it('should call miseEnGestion query and add missing value', () => {
      const demandeXRM: IDemandeXRM = { id: 11627 };
      const miseEnGestion: IMiseEnGestion = { id: 9935 };
      demandeXRM.miseEnGestion = miseEnGestion;

      const miseEnGestionCollection: IMiseEnGestion[] = [{ id: 9935 }];
      jest.spyOn(miseEnGestionService, 'query').mockReturnValue(of(new HttpResponse({ body: miseEnGestionCollection })));
      const expectedCollection: IMiseEnGestion[] = [miseEnGestion, ...miseEnGestionCollection];
      jest.spyOn(miseEnGestionService, 'addMiseEnGestionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ demandeXRM });
      comp.ngOnInit();

      expect(miseEnGestionService.query).toHaveBeenCalled();
      expect(miseEnGestionService.addMiseEnGestionToCollectionIfMissing).toHaveBeenCalledWith(miseEnGestionCollection, miseEnGestion);
      expect(comp.miseEnGestionsCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const demandeXRM: IDemandeXRM = { id: 11627 };
      const pmEntreprise: IPmEntreprise = { id: 16964 };
      demandeXRM.pmEntreprise = pmEntreprise;
      const miseEnGestion: IMiseEnGestion = { id: 9935 };
      demandeXRM.miseEnGestion = miseEnGestion;

      activatedRoute.data = of({ demandeXRM });
      comp.ngOnInit();

      expect(comp.pmEntreprisesCollection).toContainEqual(pmEntreprise);
      expect(comp.miseEnGestionsCollection).toContainEqual(miseEnGestion);
      expect(comp.demandeXRM).toEqual(demandeXRM);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeXRM>>();
      const demandeXRM = { id: 16042 };
      jest.spyOn(demandeXRMFormService, 'getDemandeXRM').mockReturnValue(demandeXRM);
      jest.spyOn(demandeXRMService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeXRM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeXRM }));
      saveSubject.complete();

      // THEN
      expect(demandeXRMFormService.getDemandeXRM).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(demandeXRMService.update).toHaveBeenCalledWith(expect.objectContaining(demandeXRM));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeXRM>>();
      const demandeXRM = { id: 16042 };
      jest.spyOn(demandeXRMFormService, 'getDemandeXRM').mockReturnValue({ id: null });
      jest.spyOn(demandeXRMService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeXRM: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: demandeXRM }));
      saveSubject.complete();

      // THEN
      expect(demandeXRMFormService.getDemandeXRM).toHaveBeenCalled();
      expect(demandeXRMService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDemandeXRM>>();
      const demandeXRM = { id: 16042 };
      jest.spyOn(demandeXRMService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ demandeXRM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(demandeXRMService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePmEntreprise', () => {
      it('should forward to pmEntrepriseService', () => {
        const entity = { id: 16964 };
        const entity2 = { id: 18362 };
        jest.spyOn(pmEntrepriseService, 'comparePmEntreprise');
        comp.comparePmEntreprise(entity, entity2);
        expect(pmEntrepriseService.comparePmEntreprise).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMiseEnGestion', () => {
      it('should forward to miseEnGestionService', () => {
        const entity = { id: 9935 };
        const entity2 = { id: 12141 };
        jest.spyOn(miseEnGestionService, 'compareMiseEnGestion');
        comp.compareMiseEnGestion(entity, entity2);
        expect(miseEnGestionService.compareMiseEnGestion).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
