import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';

import { BankAccount } from '../../models/bankaccount';
import { Constants } from '../../app.constants';

@Injectable()
export class BankAccountService {

    private path: string;
    private baseUrl: string;
    constructor(private http: Http, private router: Router, private constants: Constants) {
        this.path = 'api/bankAccount';
        this.baseUrl = constants.api_url;
    }

    create(bankAccount: BankAccount): Observable<BankAccount[]> {
        return this.http.post(this.baseUrl + this.path, bankAccount)
            .map(res => (res.json() as BankAccount[]));
    }

    getMaxAccountNumber(): Observable<number> {
        return this.http.get(this.baseUrl + this.path +"/maxAccountNumber")
            .map(res => (res.json() as number));
    }
}
