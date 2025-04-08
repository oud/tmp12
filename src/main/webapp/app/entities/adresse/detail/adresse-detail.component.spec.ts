import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AdresseDetailComponent } from './adresse-detail.component';

describe('Adresse Management Detail Component', () => {
  let comp: AdresseDetailComponent;
  let fixture: ComponentFixture<AdresseDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdresseDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./adresse-detail.component').then(m => m.AdresseDetailComponent),
              resolve: { adresse: () => of({ id: 31795 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AdresseDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdresseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load adresse on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AdresseDetailComponent);

      // THEN
      expect(instance.adresse()).toEqual(expect.objectContaining({ id: 31795 }));
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
