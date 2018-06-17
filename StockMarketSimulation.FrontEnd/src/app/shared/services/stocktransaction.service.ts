import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router';

import { StockTransaction,StockTransactionFull } from '../../models/stocktransaction';
import { Constants } from '../../app.constants';

@Injectable()
export class StockTransactionService {

    private path: string;
    private baseUrl: string;
    constructor(private http: Http, private router: Router, private constants: Constants) {
        this.path = 'api/StockTransaction';
        this.baseUrl = constants.api_url;
    }

    getSellingItem(bankAccountId: number, roundId: number): Observable<StockTransactionFull[]> {
        return this.http.get(this.baseUrl + this.path + "/GetSoldItem?bankId=" + bankAccountId + "&roundId=" + roundId)
            .map(res => (res.json() as StockTransactionFull[]));
    }

    sell(stockTransactionId: number, qty: number, sellingPrice: number, turnId: number, bankAccoundId: number): Observable<Response> {
        return this.http.get(this.baseUrl + this.path + "/sell?stockTransactionId=" + stockTransactionId + "&qty=" + qty + "&sellingPrice=" + sellingPrice + "&turnId=" + turnId + "&bankAccoundId=" + bankAccoundId)
            .map(res => (res.json() as Response));
    }

    buy(stockId: number, qty: number, turnId: number, bankAccoundId: number): Observable<Response> {
        return this.http.get(this.baseUrl + this.path + "/sell?stockId=" + stockId + "&qty=" + qty + "&turnId=" + turnId + "&turnId=" + turnId + "&bankAccoundId=" + bankAccoundId)
            .map(res => (res.json() as Response));
    }

    history(roundId: number, bankAccoundId: number): Observable<StockTransaction[]> {
        return this.http.get(this.baseUrl + this.path + "/History?roundId=" + roundId + "&bankAccoundId=" + bankAccoundId)
            .map(res => (res.json() as StockTransaction[]));
    }
}
