import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';

import { Stock, AnalystModel } from '../../models/stock';
import { Constants } from '../../app.constants';

@Injectable()
export class StockService {

    private path: string;
    private baseUrl: string;
    constructor(private http: Http, private router: Router, private constants: Constants) {
        this.path = 'api/Stock';
        this.baseUrl = constants.api_url;
    }

    getBtSectorId(sectorId:number): Observable<Stock[]> {
        return this.http.get(this.baseUrl + this.path + "/GetBySectorId?sectorId=" + sectorId)
            .map(res => (res.json() as Stock[]));
    }


    getDataForPredicate(): Observable<AnalystModel[]> {
        return this.http.get(this.baseUrl + this.path + "/getDataForPredicate")
            .map(res => (res.json() as AnalystModel[]));
    }
}
