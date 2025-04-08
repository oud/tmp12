import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IEmail } from '../email.model';

@Component({
  selector: 'jhi-email-detail',
  templateUrl: './email-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class EmailDetailComponent {
  email = input<IEmail | null>(null);

  previousState(): void {
    window.history.back();
  }
}
