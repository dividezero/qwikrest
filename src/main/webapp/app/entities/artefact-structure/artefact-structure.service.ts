import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ArtefactStructure } from './artefact-structure.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArtefactStructureService {

    private resourceUrl = 'api/artefact-structures';
    private resourceSearchUrl = 'api/_search/artefact-structures';

    constructor(private http: Http) { }

    create(artefactStructure: ArtefactStructure): Observable<ArtefactStructure> {
        const copy = this.convert(artefactStructure);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(artefactStructure: ArtefactStructure): Observable<ArtefactStructure> {
        const copy = this.convert(artefactStructure);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ArtefactStructure> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(artefactStructure: ArtefactStructure): ArtefactStructure {
        const copy: ArtefactStructure = Object.assign({}, artefactStructure);
        return copy;
    }
}
