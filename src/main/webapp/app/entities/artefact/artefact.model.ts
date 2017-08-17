import { Project } from '../project';
export class Artefact {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public project?: Project,
    ) {
    }
}
