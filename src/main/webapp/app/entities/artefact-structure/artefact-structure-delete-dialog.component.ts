import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ArtefactStructure } from './artefact-structure.model';
import { ArtefactStructurePopupService } from './artefact-structure-popup.service';
import { ArtefactStructureService } from './artefact-structure.service';

@Component({
    selector: 'jhi-artefact-structure-delete-dialog',
    templateUrl: './artefact-structure-delete-dialog.component.html'
})
export class ArtefactStructureDeleteDialogComponent {

    artefactStructure: ArtefactStructure;

    constructor(
        private artefactStructureService: ArtefactStructureService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.artefactStructureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'artefactStructureListModification',
                content: 'Deleted an artefactStructure'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artefact-structure-delete-popup',
    template: ''
})
export class ArtefactStructureDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artefactStructurePopupService: ArtefactStructurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.artefactStructurePopupService
                .open(ArtefactStructureDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
