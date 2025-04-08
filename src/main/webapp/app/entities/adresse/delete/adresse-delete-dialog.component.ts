import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAdresse } from '../adresse.model';
import { AdresseService } from '../service/adresse.service';

@Component({
  templateUrl: './adresse-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AdresseDeleteDialogComponent {
  adresse?: IAdresse;

  protected adresseService = inject(AdresseService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adresseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
