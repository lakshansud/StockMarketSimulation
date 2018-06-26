import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';

import { Constants } from '../../app.constants';
import { Broker, StartResponce } from '../../models/broker';
import { Turn } from '../../models/turn';
@Injectable()
export class BrokerService {

    private path: string;
    private baseUrl: string;
    constructor(private http: Http, private router: Router, private constants: Constants) {
        this.path = 'api/broker';
        this.baseUrl = constants.api_url;
    }

    create(bankAccountId:number): Observable<number> {
        return this.http.get(this.baseUrl + this.path + "/create?bankAccountId=" + bankAccountId)
            .map(res => (res.json() as number));
    }

    start(): Observable<StartResponce> {
        return this.http.get(this.baseUrl + this.path + "/start")
            .map(res => (res.json() as StartResponce));
    }

    getCurrentTurn(): Observable<Turn> {
        return this.http.get(this.baseUrl + "api/Turn/getCurrentTurn")
            .map(res => (res.json() as Turn));
    }
}
