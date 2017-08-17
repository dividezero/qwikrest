import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { QwikrestProjectModule } from './project/project.module';
import { QwikrestArtefactModule } from './artefact/artefact.module';
import { QwikrestArtefactStructureModule } from './artefact-structure/artefact-structure.module';
import { QwikrestArtefactDataModule } from './artefact-data/artefact-data.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        QwikrestProjectModule,
        QwikrestArtefactModule,
        QwikrestArtefactStructureModule,
        QwikrestArtefactDataModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QwikrestEntityModule {}
