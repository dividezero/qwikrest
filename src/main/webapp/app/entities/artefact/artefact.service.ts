import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Artefact } from './artefact.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArtefactService {

    private resourceUrl = 'api/artefacts';
    private resourceSearchUrl = 'api/_search/artefacts';

    constructor(private http: Http) { }

    create(artefact: Artefact): Observable<Artefact> {
        const copy = this.convert(artefact);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(artefact: Artefact): Observable<Artefact> {
        const copy = this.convert(artefact);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Artefact> {
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

    private convert(artefact: Artefact): Artefact {
        const copy: Artefact = Object.assign({}, artefact);
        return copy;
    }
}
