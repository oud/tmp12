import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';
import { IPmEntreprise } from '../pm-entreprise.model';

@Component({
  selector: 'jhi-pm-entreprise-detail',
  templateUrl: './pm-entreprise-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatePipe],
})
export class PmEntrepriseDetailComponent {
  pmEntreprise = input<IPmEntreprise | null>(null);

  previousState(): void {
    window.history.back();
  }
}
