import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QwikrestSharedModule } from '../../shared';
import {
    ArtefactService,
    ArtefactPopupService,
    ArtefactComponent,
    ArtefactDetailComponent,
    ArtefactDialogComponent,
    ArtefactPopupComponent,
    ArtefactDeletePopupComponent,
    ArtefactDeleteDialogComponent,
    artefactRoute,
    artefactPopupRoute,
} from './';

const ENTITY_STATES = [
    ...artefactRoute,
    ...artefactPopupRoute,
];

@NgModule({
    imports: [
        QwikrestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArtefactComponent,
        ArtefactDetailComponent,
        ArtefactDialogComponent,
        ArtefactDeleteDialogComponent,
        ArtefactPopupComponent,
        ArtefactDeletePopupComponent,
    ],
    entryComponents: [
        ArtefactComponent,
        ArtefactDialogComponent,
        ArtefactPopupComponent,
        ArtefactDeleteDialogComponent,
        ArtefactDeletePopupComponent,
    ],
    providers: [
        ArtefactService,
        ArtefactPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QwikrestArtefactModule {}
