import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TelephoneDetailComponent } from './telephone-detail.component';

describe('Telephone Management Detail Component', () => {
  let comp: TelephoneDetailComponent;
  let fixture: ComponentFixture<TelephoneDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TelephoneDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./telephone-detail.component').then(m => m.TelephoneDetailComponent),
              resolve: { telephone: () => of({ id: 29884 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TelephoneDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TelephoneDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load telephone on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TelephoneDetailComponent);

      // THEN
      expect(instance.telephone()).toEqual(expect.objectContaining({ id: 29884 }));
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
