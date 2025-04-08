import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IDemandeXRM } from '../demande-xrm.model';

@Component({
  selector: 'jhi-demande-xrm-detail',
  templateUrl: './demande-xrm-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class DemandeXRMDetailComponent {
  demandeXRM = input<IDemandeXRM | null>(null);

  previousState(): void {
    window.history.back();
  }
}
