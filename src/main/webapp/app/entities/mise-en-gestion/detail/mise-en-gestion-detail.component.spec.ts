import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MiseEnGestionDetailComponent } from './mise-en-gestion-detail.component';

describe('MiseEnGestion Management Detail Component', () => {
  let comp: MiseEnGestionDetailComponent;
  let fixture: ComponentFixture<MiseEnGestionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MiseEnGestionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./mise-en-gestion-detail.component').then(m => m.MiseEnGestionDetailComponent),
              resolve: { miseEnGestion: () => of({ id: 9935 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MiseEnGestionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MiseEnGestionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load miseEnGestion on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MiseEnGestionDetailComponent);

      // THEN
      expect(instance.miseEnGestion()).toEqual(expect.objectContaining({ id: 9935 }));
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
