import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';

import { Sector } from '../../models/sector';
import { Constants } from '../../app.constants';

@Injectable()
export class SectorService {

    private path: string;
    private baseUrl: string;
    constructor(private http: Http, private router: Router, private constants: Constants) {
        this.path = 'api/Sector';
        this.baseUrl = constants.api_url;
    }

    getAll(): Observable<Sector[]> {
        return this.http.get(this.baseUrl + this.path)
            .map(res => (res.json() as Sector[]));
    }
}
