import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ArtefactData } from './artefact-data.model';
import { ArtefactDataService } from './artefact-data.service';
@Injectable()
export class ArtefactDataPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private artefactDataService: ArtefactDataService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.artefactDataService.find(id).subscribe((artefactData) => {
                this.artefactDataModalRef(component, artefactData);
            });
        } else {
            return this.artefactDataModalRef(component, new ArtefactData());
        }
    }

    artefactDataModalRef(component: Component, artefactData: ArtefactData): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.artefactData = artefactData;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
