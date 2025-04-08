import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IAdresse } from '../adresse.model';

@Component({
  selector: 'jhi-adresse-detail',
  templateUrl: './adresse-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class AdresseDetailComponent {
  adresse = input<IAdresse | null>(null);

  previousState(): void {
    window.history.back();
  }
}
