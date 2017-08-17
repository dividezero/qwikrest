import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QwikrestSharedModule } from '../../shared';
import {
    ArtefactDataService,
    ArtefactDataPopupService,
    ArtefactDataComponent,
    ArtefactDataDetailComponent,
    ArtefactDataDialogComponent,
    ArtefactDataPopupComponent,
    ArtefactDataDeletePopupComponent,
    ArtefactDataDeleteDialogComponent,
    artefactDataRoute,
    artefactDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...artefactDataRoute,
    ...artefactDataPopupRoute,
];

@NgModule({
    imports: [
        QwikrestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArtefactDataComponent,
        ArtefactDataDetailComponent,
        ArtefactDataDialogComponent,
        ArtefactDataDeleteDialogComponent,
        ArtefactDataPopupComponent,
        ArtefactDataDeletePopupComponent,
    ],
    entryComponents: [
        ArtefactDataComponent,
        ArtefactDataDialogComponent,
        ArtefactDataPopupComponent,
        ArtefactDataDeleteDialogComponent,
        ArtefactDataDeletePopupComponent,
    ],
    providers: [
        ArtefactDataService,
        ArtefactDataPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QwikrestArtefactDataModule {}
