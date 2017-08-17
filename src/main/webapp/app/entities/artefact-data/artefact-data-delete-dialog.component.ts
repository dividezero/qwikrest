import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ArtefactData } from './artefact-data.model';
import { ArtefactDataPopupService } from './artefact-data-popup.service';
import { ArtefactDataService } from './artefact-data.service';

@Component({
    selector: 'jhi-artefact-data-delete-dialog',
    templateUrl: './artefact-data-delete-dialog.component.html'
})
export class ArtefactDataDeleteDialogComponent {

    artefactData: ArtefactData;

    constructor(
        private artefactDataService: ArtefactDataService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.artefactDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'artefactDataListModification',
                content: 'Deleted an artefactData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artefact-data-delete-popup',
    template: ''
})
export class ArtefactDataDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artefactDataPopupService: ArtefactDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.artefactDataPopupService
                .open(ArtefactDataDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
