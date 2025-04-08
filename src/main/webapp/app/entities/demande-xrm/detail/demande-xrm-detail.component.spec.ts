import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DemandeXRMDetailComponent } from './demande-xrm-detail.component';

describe('DemandeXRM Management Detail Component', () => {
  let comp: DemandeXRMDetailComponent;
  let fixture: ComponentFixture<DemandeXRMDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemandeXRMDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./demande-xrm-detail.component').then(m => m.DemandeXRMDetailComponent),
              resolve: { demandeXRM: () => of({ id: 16042 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DemandeXRMDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DemandeXRMDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load demandeXRM on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DemandeXRMDetailComponent);

      // THEN
      expect(instance.demandeXRM()).toEqual(expect.objectContaining({ id: 16042 }));
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
