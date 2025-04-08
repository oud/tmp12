import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDemandeXRM } from '../demande-xrm.model';
import { DemandeXRMService } from '../service/demande-xrm.service';

@Component({
  templateUrl: './demande-xrm-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DemandeXRMDeleteDialogComponent {
  demandeXRM?: IDemandeXRM;

  protected demandeXRMService = inject(DemandeXRMService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeXRMService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
