import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PmEntrepriseService } from '../service/pm-entreprise.service';
import { IPmEntreprise } from '../pm-entreprise.model';
import { PmEntrepriseFormService } from './pm-entreprise-form.service';

import { PmEntrepriseUpdateComponent } from './pm-entreprise-update.component';

describe('PmEntreprise Management Update Component', () => {
  let comp: PmEntrepriseUpdateComponent;
  let fixture: ComponentFixture<PmEntrepriseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pmEntrepriseFormService: PmEntrepriseFormService;
  let pmEntrepriseService: PmEntrepriseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PmEntrepriseUpdateComponent],
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
      .overrideTemplate(PmEntrepriseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PmEntrepriseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pmEntrepriseFormService = TestBed.inject(PmEntrepriseFormService);
    pmEntrepriseService = TestBed.inject(PmEntrepriseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const pmEntreprise: IPmEntreprise = { id: 18362 };

      activatedRoute.data = of({ pmEntreprise });
      comp.ngOnInit();

      expect(comp.pmEntreprise).toEqual(pmEntreprise);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPmEntreprise>>();
      const pmEntreprise = { id: 16964 };
      jest.spyOn(pmEntrepriseFormService, 'getPmEntreprise').mockReturnValue(pmEntreprise);
      jest.spyOn(pmEntrepriseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pmEntreprise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pmEntreprise }));
      saveSubject.complete();

      // THEN
      expect(pmEntrepriseFormService.getPmEntreprise).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pmEntrepriseService.update).toHaveBeenCalledWith(expect.objectContaining(pmEntreprise));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPmEntreprise>>();
      const pmEntreprise = { id: 16964 };
      jest.spyOn(pmEntrepriseFormService, 'getPmEntreprise').mockReturnValue({ id: null });
      jest.spyOn(pmEntrepriseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pmEntreprise: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pmEntreprise }));
      saveSubject.complete();

      // THEN
      expect(pmEntrepriseFormService.getPmEntreprise).toHaveBeenCalled();
      expect(pmEntrepriseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPmEntreprise>>();
      const pmEntreprise = { id: 16964 };
      jest.spyOn(pmEntrepriseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pmEntreprise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pmEntrepriseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
