import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmailDetailComponent } from './email-detail.component';

describe('Email Management Detail Component', () => {
  let comp: EmailDetailComponent;
  let fixture: ComponentFixture<EmailDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./email-detail.component').then(m => m.EmailDetailComponent),
              resolve: { email: () => of({ id: 20273 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmailDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load email on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmailDetailComponent);

      // THEN
      expect(instance.email()).toEqual(expect.objectContaining({ id: 20273 }));
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
