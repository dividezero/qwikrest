import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Artefact } from './artefact.model';
import { ArtefactPopupService } from './artefact-popup.service';
import { ArtefactService } from './artefact.service';
import { Project, ProjectService } from '../project';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artefact-dialog',
    templateUrl: './artefact-dialog.component.html'
})
export class ArtefactDialogComponent implements OnInit {

    artefact: Artefact;
    authorities: any[];
    isSaving: boolean;

    projects: Project[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private artefactService: ArtefactService,
        private projectService: ProjectService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.projectService.query()
            .subscribe((res: ResponseWrapper) => { this.projects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.artefact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.artefactService.update(this.artefact));
        } else {
            this.subscribeToSaveResponse(
                this.artefactService.create(this.artefact));
        }
    }

    private subscribeToSaveResponse(result: Observable<Artefact>) {
        result.subscribe((res: Artefact) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Artefact) {
        this.eventManager.broadcast({ name: 'artefactListModification', content: 'OK'});
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

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-artefact-popup',
    template: ''
})
export class ArtefactPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artefactPopupService: ArtefactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.artefactPopupService
                    .open(ArtefactDialogComponent, params['id']);
            } else {
                this.modalRef = this.artefactPopupService
                    .open(ArtefactDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
