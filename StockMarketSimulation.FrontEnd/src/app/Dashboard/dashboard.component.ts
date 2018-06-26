import { Component, OnInit } from '@angular/core';
import { StockTransaction, StockTransactionFull } from '../models/stocktransaction';
import { Stock } from '../models/stock';
import { ValueChangeForYears } from '../models/stockpricehistory';
import { Sector } from '../models/sector';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { StockTransactionService } from '../shared/services/stocktransaction.service';
import { SectorService } from '../shared/services/sector.service';
import { StockService } from '../shared/services/stock.service';
import { BrokerService } from '../shared/services/broker.service';
import { BankAccountService } from '../shared/services/bankaccount.service';
import { Broker, StartResponce } from '../models/broker';
import { CurrentBankInfo,BankAccount } from '../models/bankaccount';


@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html'
})
export class DashBoardComponent implements OnInit {

    currentBankInfo: CurrentBankInfo = new CurrentBankInfo();
    loginUserId = 0;
    bankAccountId = 0;
    error = "";
    roundId: number = 0;
    transactionHistoryList: StockTransactionFull[] = new Array<StockTransactionFull>();
    view: any[] = [700, 400];
    sectorList: Sector[] = new Array<Sector>();
    // options
    showXAxis = true;
    users = new Array<BankAccount>();
    today = new Date().toLocaleTimeString();
    showYAxis = true;
    gradient = false;
    showLegend = true;
    showXAxisLabel = true;
    xAxisLabel = 'Year';
    showYAxisLabel = true;
    yAxisLabel = 'Price';
    selectedChartSector: Sector = new Sector();
    colorScheme = {
        domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
    };
    turn = 1;
    // line, area
    autoScale = true;

    onSelect(event) {
        console.log(event);
    }

    single = [
        {
            "name": "ACER",
            "value": 8940000
        },
        {
            "name": "HP",
            "value": 5000000
        },
        {
            "name": "DELL",
            "value": 57200000
        }
    ];

    multi = [];

    constructor(private fb: FormBuilder, private spinner: NgxSpinnerService, private bankAccountService: BankAccountService, private brokerService: BrokerService, private stockTransactionService: StockTransactionService, private sectorService: SectorService, private stockService: StockService) {

    }

    ngOnInit() {
        this.getSectors();
        var round = localStorage.getItem('roundId');
        if (round)
            this.roundId = +round;

        var turn = localStorage.getItem('Turn');
        if (turn)
            this.turn = +turn;

        var isLogin = localStorage.getItem('isLogin');
        if (!isLogin) {

            this.error = "You are not login to this app. Please login before play the game.";
            setTimeout(function () {
                this.router.navigate(['login']);
            }.bind(this), 5000);
        } else {
            var loginUserId = localStorage.getItem('loginUserId');
            this.getCurrentUserInfo(+loginUserId);
            this.bankAccountId = +loginUserId;
        }
        this.getHistory();
        this.getCurrentUserInfo(+loginUserId);
        this.getUsersInfo();


        setInterval(() => {
            console.log("Turn is going to refresh...");
            this.getCurrentTurn();
        }, 5000);
    }

    getCurrentTurn(): void {
        this.brokerService.getCurrentTurn()
            .subscribe((data: any) => {
                    this.turn = data.Turn
            },
            (error: Response) => {

            });
    }

    onChangeChartSelectBox(sectorId: number): void {
        this.selectedChartSector.Id = sectorId;
        this.getValueChangeForYears();
    }

    getSectors(): void {
        this.spinner.show();
        this.sectorService.getAll()
            .subscribe((data: Sector[]) => {
                this.sectorList = data;
                this.selectedChartSector.Id = 1;
                this.getValueChangeForYears();
                this.spinner.hide();
            },
            (error: Response) => {
                this.spinner.hide();
            });
    }

    getUsersInfo(): void {
        this.spinner.show();
        this.bankAccountService.GetUsers()
            .subscribe((data: BankAccount[]) => {
                this.users = data;
                this.spinner.hide();
            },
            (error: Response) => {
                this.spinner.hide();
            });
    }

    getValueChangeForYears(): void {
        this.spinner.show();
        this.stockService.getValueChangeForYears(this.selectedChartSector.Id)
            .subscribe((data: ValueChangeForYears[]) => {
                this.multi = data;
                this.spinner.hide();
            },
            (error: Response) => {

                this.spinner.hide();
            });
    }

    getCurrentUserInfo(userId: number): void {
        this.spinner.show();
        this.bankAccountService.getCurrentUserInfo(userId)
            .subscribe((data: CurrentBankInfo) => {
                this.currentBankInfo = data;
                this.spinner.hide();
            },
            (error: Response) => {
                this.spinner.hide();
            });
    }

    getHistory(): void {
        this.spinner.show();
        this.stockTransactionService.history(this.roundId, this.bankAccountId)
            .subscribe((data: StockTransactionFull[]) => {
                this.transactionHistoryList = data;
                this.spinner.hide();
            },
            (error: Response) => {
                this.spinner.hide();
            });

    }
}
