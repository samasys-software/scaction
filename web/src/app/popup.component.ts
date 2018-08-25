import {Component } from '@angular/core';
import {NgbModal, ModalDismissReasons, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Content } from '@angular/compiler/src/render3/r3_ast';

@Component({
  selector: 'app-ngbd-modal-content',
  template: `
  <div class="modal-header">
    <h4 class="modal-title">Hi there!</h4>
    <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <p>Hello!</p>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-outline-dark" (click)="activeModal.close('Close click')">Close</button>
  </div>
`
})
export class PopupContent {

  constructor(public activeModal: NgbActiveModal) {}
}

@Component({
  selector: 'app-popup',
  templateUrl: './popup.component.html'
})
export class PopUpComponent {
  closeResult: string;

  constructor(private modalService: NgbModal) {}

  open() {
    /*this.modalService.open('', {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });*/
    const modalRef = this.modalService.open(PopupContent);
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }
}
