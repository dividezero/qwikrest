import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ArtefactStructure } from './artefact-structure.model';
import { ArtefactStructureService } from './artefact-structure.service';

@Component({
    selector: 'jhi-artefact-structure-detail',
    templateUrl: './artefact-structure-detail.component.html'
})
export class ArtefactStructureDetailComponent implements OnInit, OnDestroy {

    artefactStructure: ArtefactStructure;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private artefactStructureService: ArtefactStructureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArtefactStructures();
    }

    load(id) {
        this.artefactStructureService.find(id).subscribe((artefactStructure) => {
            this.artefactStructure = artefactStructure;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArtefactStructures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'artefactStructureListModification',
            (response) => this.load(this.artefactStructure.id)
        );
    }
}
