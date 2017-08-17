import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Artefact } from './artefact.model';
import { ArtefactPopupService } from './artefact-popup.service';
import { ArtefactService } from './artefact.service';

@Component({
    selector: 'jhi-artefact-delete-dialog',
    templateUrl: './artefact-delete-dialog.component.html'
})
export class ArtefactDeleteDialogComponent {

    artefact: Artefact;

    constructor(
        private artefactService: ArtefactService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.artefactService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'artefactListModification',
                content: 'Deleted an artefact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artefact-delete-popup',
    template: ''
})
export class ArtefactDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artefactPopupService: ArtefactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.artefactPopupService
                .open(ArtefactDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
