import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PmEntrepriseDetailComponent } from './pm-entreprise-detail.component';

describe('PmEntreprise Management Detail Component', () => {
  let comp: PmEntrepriseDetailComponent;
  let fixture: ComponentFixture<PmEntrepriseDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PmEntrepriseDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./pm-entreprise-detail.component').then(m => m.PmEntrepriseDetailComponent),
              resolve: { pmEntreprise: () => of({ id: 16964 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PmEntrepriseDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PmEntrepriseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load pmEntreprise on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PmEntrepriseDetailComponent);

      // THEN
      expect(instance.pmEntreprise()).toEqual(expect.objectContaining({ id: 16964 }));
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
