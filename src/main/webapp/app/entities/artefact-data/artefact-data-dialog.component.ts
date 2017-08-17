import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ArtefactData } from './artefact-data.model';
import { ArtefactDataPopupService } from './artefact-data-popup.service';
import { ArtefactDataService } from './artefact-data.service';
import { Artefact, ArtefactService } from '../artefact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artefact-data-dialog',
    templateUrl: './artefact-data-dialog.component.html'
})
export class ArtefactDataDialogComponent implements OnInit {

    artefactData: ArtefactData;
    authorities: any[];
    isSaving: boolean;

    artefacts: Artefact[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private artefactDataService: ArtefactDataService,
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
        if (this.artefactData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.artefactDataService.update(this.artefactData));
        } else {
            this.subscribeToSaveResponse(
                this.artefactDataService.create(this.artefactData));
        }
    }

    private subscribeToSaveResponse(result: Observable<ArtefactData>) {
        result.subscribe((res: ArtefactData) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ArtefactData) {
        this.eventManager.broadcast({ name: 'artefactDataListModification', content: 'OK'});
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
    selector: 'jhi-artefact-data-popup',
    template: ''
})
export class ArtefactDataPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artefactDataPopupService: ArtefactDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.artefactDataPopupService
                    .open(ArtefactDataDialogComponent, params['id']);
            } else {
                this.modalRef = this.artefactDataPopupService
                    .open(ArtefactDataDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
