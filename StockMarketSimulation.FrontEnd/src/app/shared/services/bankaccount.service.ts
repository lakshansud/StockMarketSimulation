import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';

import { BankAccount, LoginResponce, CurrentBankInfo } from '../../models/bankaccount';
import { Constants } from '../../app.constants';
import { Marks } from '../../models/marks';

@Injectable()
export class BankAccountService {

    private path: string;
    private baseUrl: string;
    constructor(private http: Http, private router: Router, private constants: Constants) {
        this.path = 'api/bankAccount';
        this.baseUrl = constants.api_url;
    }

    create(bankAccount: BankAccount): Observable<BankAccount> {
        return this.http.get(this.baseUrl + this.path + "/create?playerName=" + bankAccount.PlayerName + "&userName=" + bankAccount.UserName + "&password=" + bankAccount.Password + "&accountNumber=" + bankAccount.AccountNumber)
            .map(res => (res.json() as BankAccount));
    }

    login(userName: string, passW: string): Observable<LoginResponce> {
        return this.http.get(this.baseUrl + this.path + "/login?username=" + userName + "&password=" + passW )
            .map(res => (res.json() as LoginResponce));
    }

    getMaxAccountNumber(): Observable<number> {
        return this.http.get(this.baseUrl + this.path +"/maxAccountNumber")
            .map(res => (res.json() as number));
    }

    GetUsersMarks(roundId:number): Observable<Marks[]> {
        return this.http.get(this.baseUrl + this.path + "/getUsersMarks?roundId=" + roundId)
            .map(res => (res.json() as Marks[]));
    }

    GetUsers(): Observable<BankAccount[]> {
        return this.http.get(this.baseUrl + this.path + "/getUsers")
            .map(res => (res.json() as BankAccount[]));
    }

    getCurrentUserInfo(userId:number): Observable<CurrentBankInfo> {
        return this.http.get(this.baseUrl + this.path + "/currentBankInfo?bankAccountId=" + userId)
            .map(res => (res.json() as CurrentBankInfo));
    }
}
