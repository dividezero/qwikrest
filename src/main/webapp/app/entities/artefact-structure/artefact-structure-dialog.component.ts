import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ArtefactStructure } from './artefact-structure.model';
import { ArtefactStructurePopupService } from './artefact-structure-popup.service';
import { ArtefactStructureService } from './artefact-structure.service';
import { Artefact, ArtefactService } from '../artefact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artefact-structure-dialog',
    templateUrl: './artefact-structure-dialog.component.html'
})
export class ArtefactStructureDialogComponent implements OnInit {

    artefactStructure: ArtefactStructure;
    authorities: any[];
    isSaving: boolean;

    artefacts: Artefact[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private artefactStructureService: ArtefactStructureService,
        private artefactService: ArtefactService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.artefactService.query()
            .subscribe((res: ResponseWrapper) => { this.artefacts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.artefactStructure.id !== undefined) {
            this.subscribeToSaveResponse(
                this.artefactStructureService.update(this.artefactStructure));
        } else {
            this.subscribeToSaveResponse(
                this.artefactStructureService.create(this.artefactStructure));
        }
    }

    private subscribeToSaveResponse(result: Observable<ArtefactStructure>) {
        result.subscribe((res: ArtefactStructure) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ArtefactStructure) {
        this.eventManager.broadcast({ name: 'artefactStructureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackArtefactById(index: number, item: Artefact) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-artefact-structure-popup',
    template: ''
})
export class ArtefactStructurePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artefactStructurePopupService: ArtefactStructurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.artefactStructurePopupService
                    .open(ArtefactStructureDialogComponent, params['id']);
            } else {
                this.modalRef = this.artefactStructurePopupService
                    .open(ArtefactStructureDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
