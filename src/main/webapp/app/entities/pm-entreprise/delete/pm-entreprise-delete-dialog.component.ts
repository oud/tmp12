import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPmEntreprise } from '../pm-entreprise.model';
import { PmEntrepriseService } from '../service/pm-entreprise.service';

@Component({
  templateUrl: './pm-entreprise-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PmEntrepriseDeleteDialogComponent {
  pmEntreprise?: IPmEntreprise;

  protected pmEntrepriseService = inject(PmEntrepriseService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pmEntrepriseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
