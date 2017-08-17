import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Artefact } from './artefact.model';
import { ArtefactService } from './artefact.service';

@Component({
    selector: 'jhi-artefact-detail',
    templateUrl: './artefact-detail.component.html'
})
export class ArtefactDetailComponent implements OnInit, OnDestroy {

    artefact: Artefact;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private artefactService: ArtefactService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArtefacts();
    }

    load(id) {
        this.artefactService.find(id).subscribe((artefact) => {
            this.artefact = artefact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArtefacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'artefactListModification',
            (response) => this.load(this.artefact.id)
        );
    }
}
