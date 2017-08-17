import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ArtefactData } from './artefact-data.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArtefactDataService {

    private resourceUrl = 'api/artefact-data';
    private resourceSearchUrl = 'api/_search/artefact-data';

    constructor(private http: Http) { }

    create(artefactData: ArtefactData): Observable<ArtefactData> {
        const copy = this.convert(artefactData);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(artefactData: ArtefactData): Observable<ArtefactData> {
        const copy = this.convert(artefactData);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ArtefactData> {
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

    private convert(artefactData: ArtefactData): ArtefactData {
        const copy: ArtefactData = Object.assign({}, artefactData);
        return copy;
    }
}
