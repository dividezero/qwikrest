import { Artefact } from '../artefact';
export class ArtefactData {
    constructor(
        public id?: number,
        public value?: string,
        public artefact?: Artefact,
    ) {
    }
}
