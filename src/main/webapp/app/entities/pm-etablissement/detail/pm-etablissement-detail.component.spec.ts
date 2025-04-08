import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PmEtablissementDetailComponent } from './pm-etablissement-detail.component';

describe('PmEtablissement Management Detail Component', () => {
  let comp: PmEtablissementDetailComponent;
  let fixture: ComponentFixture<PmEtablissementDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PmEtablissementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./pm-etablissement-detail.component').then(m => m.PmEtablissementDetailComponent),
              resolve: { pmEtablissement: () => of({ id: 16761 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PmEtablissementDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PmEtablissementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load pmEtablissement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PmEtablissementDetailComponent);

      // THEN
      expect(instance.pmEtablissement()).toEqual(expect.objectContaining({ id: 16761 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
