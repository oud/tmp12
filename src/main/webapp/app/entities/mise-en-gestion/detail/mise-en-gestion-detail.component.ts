import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IMiseEnGestion } from '../mise-en-gestion.model';

@Component({
  selector: 'jhi-mise-en-gestion-detail',
  templateUrl: './mise-en-gestion-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class MiseEnGestionDetailComponent {
  miseEnGestion = input<IMiseEnGestion | null>(null);

  previousState(): void {
    window.history.back();
  }
}
