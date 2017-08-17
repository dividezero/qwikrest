import { Artefact } from '../artefact';
export class ArtefactStructure {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public dataType?: string,
        public length?: number,
        public nullable?: boolean,
        public artefact?: Artefact,
    ) {
        this.nullable = false;
    }
}
