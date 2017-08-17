import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ArtefactData } from './artefact-data.model';
import { ArtefactDataService } from './artefact-data.service';

@Component({
    selector: 'jhi-artefact-data-detail',
    templateUrl: './artefact-data-detail.component.html'
})
export class ArtefactDataDetailComponent implements OnInit, OnDestroy {

    artefactData: ArtefactData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private artefactDataService: ArtefactDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArtefactData();
    }

    load(id) {
        this.artefactDataService.find(id).subscribe((artefactData) => {
            this.artefactData = artefactData;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArtefactData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'artefactDataListModification',
            (response) => this.load(this.artefactData.id)
        );
    }
}
