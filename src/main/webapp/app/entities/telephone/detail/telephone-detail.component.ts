import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { ITelephone } from '../telephone.model';

@Component({
  selector: 'jhi-telephone-detail',
  templateUrl: './telephone-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class TelephoneDetailComponent {
  telephone = input<ITelephone | null>(null);

  previousState(): void {
    window.history.back();
  }
}
