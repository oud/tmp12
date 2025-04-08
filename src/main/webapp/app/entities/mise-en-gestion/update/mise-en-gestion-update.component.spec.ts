import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MiseEnGestionService } from '../service/mise-en-gestion.service';
import { IMiseEnGestion } from '../mise-en-gestion.model';
import { MiseEnGestionFormService } from './mise-en-gestion-form.service';

import { MiseEnGestionUpdateComponent } from './mise-en-gestion-update.component';

describe('MiseEnGestion Management Update Component', () => {
  let comp: MiseEnGestionUpdateComponent;
  let fixture: ComponentFixture<MiseEnGestionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let miseEnGestionFormService: MiseEnGestionFormService;
  let miseEnGestionService: MiseEnGestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MiseEnGestionUpdateComponent],
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
      .overrideTemplate(MiseEnGestionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MiseEnGestionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    miseEnGestionFormService = TestBed.inject(MiseEnGestionFormService);
    miseEnGestionService = TestBed.inject(MiseEnGestionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const miseEnGestion: IMiseEnGestion = { id: 12141 };

      activatedRoute.data = of({ miseEnGestion });
      comp.ngOnInit();

      expect(comp.miseEnGestion).toEqual(miseEnGestion);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMiseEnGestion>>();
      const miseEnGestion = { id: 9935 };
      jest.spyOn(miseEnGestionFormService, 'getMiseEnGestion').mockReturnValue(miseEnGestion);
      jest.spyOn(miseEnGestionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ miseEnGestion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: miseEnGestion }));
      saveSubject.complete();

      // THEN
      expect(miseEnGestionFormService.getMiseEnGestion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(miseEnGestionService.update).toHaveBeenCalledWith(expect.objectContaining(miseEnGestion));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMiseEnGestion>>();
      const miseEnGestion = { id: 9935 };
      jest.spyOn(miseEnGestionFormService, 'getMiseEnGestion').mockReturnValue({ id: null });
      jest.spyOn(miseEnGestionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ miseEnGestion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: miseEnGestion }));
      saveSubject.complete();

      // THEN
      expect(miseEnGestionFormService.getMiseEnGestion).toHaveBeenCalled();
      expect(miseEnGestionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMiseEnGestion>>();
      const miseEnGestion = { id: 9935 };
      jest.spyOn(miseEnGestionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ miseEnGestion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(miseEnGestionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
