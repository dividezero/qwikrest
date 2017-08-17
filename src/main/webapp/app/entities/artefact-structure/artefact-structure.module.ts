import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QwikrestSharedModule } from '../../shared';
import {
    ArtefactStructureService,
    ArtefactStructurePopupService,
    ArtefactStructureComponent,
    ArtefactStructureDetailComponent,
    ArtefactStructureDialogComponent,
    ArtefactStructurePopupComponent,
    ArtefactStructureDeletePopupComponent,
    ArtefactStructureDeleteDialogComponent,
    artefactStructureRoute,
    artefactStructurePopupRoute,
} from './';

const ENTITY_STATES = [
    ...artefactStructureRoute,
    ...artefactStructurePopupRoute,
];

@NgModule({
    imports: [
        QwikrestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArtefactStructureComponent,
        ArtefactStructureDetailComponent,
        ArtefactStructureDialogComponent,
        ArtefactStructureDeleteDialogComponent,
        ArtefactStructurePopupComponent,
        ArtefactStructureDeletePopupComponent,
    ],
    entryComponents: [
        ArtefactStructureComponent,
        ArtefactStructureDialogComponent,
        ArtefactStructurePopupComponent,
        ArtefactStructureDeleteDialogComponent,
        ArtefactStructureDeletePopupComponent,
    ],
    providers: [
        ArtefactStructureService,
        ArtefactStructurePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QwikrestArtefactStructureModule {}
