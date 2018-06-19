import { Component,OnInit } from '@angular/core';
import { StockTransaction,StockTransactionFull } from '../models/stocktransaction';
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
import { CurrentBankInfo } from '../models/bankaccount';
@Component({
    selector: 'broker',
    templateUrl: './broker.component.html'
})
export class BrokerComponent implements OnInit {
    sellingItemList: StockTransactionFull[] = new Array<StockTransactionFull>();
    buyingItemList: Stock[] = new Array<Stock>();
    sectorList: Sector[] = new Array<Sector>();
    selectedBuySector: Sector = new Sector();
    selectedChartSector: Sector = new Sector();
    transactionHistoryList: StockTransactionFull[] = new Array<StockTransactionFull>();
    isSelectItemToSell: boolean = false;
    isSelectItemToBuy: boolean = false;
    selectedSellStockTransaction: StockTransactionFull = new StockTransactionFull();
    selectedStockToBuy: Stock = new Stock();
    sellForm: FormGroup;
    sellingQty: number = 0;
    sellingQtyError: string;
    sellingPrice: number = 0;
    sellingPriceError: string;
    buyingQty: number = 0;
    buyingError: string;
    error = "";
    isStartGame = false;
    turnId: number = 0;
    roundId: number = 0;
    currentBankInfo: CurrentBankInfo = new CurrentBankInfo();
    bankAccountId: number = 0;
    constructor(private fb: FormBuilder, private spinner: NgxSpinnerService, private bankAccountService: BankAccountService, private brokerService: BrokerService, private stockTransactionService: StockTransactionService, private sectorService: SectorService, private stockService: StockService) {

    }

    ngOnInit() {
        
        this.sellForm = this.fb.group({
            qty: ['', [Validators.required as any]],
            price: ['', [Validators.required as any]],
        });
        this.getSellingItem();
        this.getSectors();
        var isStart = localStorage.getItem('isStart');
        var turn = localStorage.getItem('TurnId');
        if (turn)
            this.turnId = +turn;

        var round = localStorage.getItem('roundId');
        if (round)
            this.roundId = +round;

        if (isStart)
            this.isStartGame = true;
        else
            this.isStartGame = false;

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
   
    getSellingItem(): void {
        this.spinner.show();
        this.stockTransactionService.getSellingItem(1,1)
            .subscribe((data: StockTransactionFull[]) => {
                this.sellingItemList = data;

                this.spinner.hide();
            },
            (error: Response) => {

                this.spinner.hide();
            });
    }

    onChangeChartSelectBox(sectorId:number): void {
        this.selectedChartSector.Id = sectorId;
        this.getValueChangeForYears();
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

    start(): void {
        this.spinner.show();
        this.brokerService.start()
            .subscribe((data: StartResponce) => {
                this.turnId = data.TurnId;
                this.roundId = data.RoundId;
                localStorage.setItem('roundId', data.RoundId.toString());
                localStorage.setItem('round', data.Round.toString());
                localStorage.setItem('TurnId', data.TurnId.toString());
                localStorage.setItem('Turn', data.Turn.toString());
                localStorage.setItem('isStart', "1");
                this.isStartGame = true;
                this.spinner.hide();
            },
            (error: Response) => {
                this.spinner.hide();
            });
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

    getStockBySectorId(selectedBuySectorId: number): void {
        this.spinner.show();
        this.stockService.getBtSectorId(selectedBuySectorId)
            .subscribe((data: Stock[]) => {
                this.buyingItemList = data;
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


    validBefore(): boolean {
        if (this.selectedSellStockTransaction.CurrentPrice < this.sellingPrice) {
            this.sellingPriceError = "Price is grater than current Price";
            return false;
        }
        if (this.selectedSellStockTransaction.Quantity < this.sellingQty) {
            this.sellingQtyError = "Quantity is grater than your available quantity";
            return false;
        }
        return true;
    }

    sell(): void {
        if (this.validBefore()) {
            this.spinner.show();
            this.stockTransactionService.sell(this.selectedSellStockTransaction.Id, this.sellingQty, this.sellingPrice, this.turnId, this.bankAccountId)
                .subscribe((data: any) => {
                    this.getHistory();
                    this.getCurrentUserInfo(this.bankAccountId);
                    this.getSellingItem();
                    this.isSelectItemToSell = false;
                    this.selectedSellStockTransaction = new StockTransactionFull();
                    this.sellingQty = 0;
                    this.sellingPrice = 0;
                    this.spinner.hide();
                },
                (error: Response) => {
                    this.spinner.hide();
                });
        }
    }

    validateBeforeBuy(): boolean {
        this.buyingError = "";
        if (this.currentBankInfo.CurrentBaniBalance < this.selectedStockToBuy.CurrentPrice * this.buyingQty) {
            this.buyingError = "Your bank balance is not enought.";
            return false;
        }
        return true;
    }

    buy(): void {
        if (this.validateBeforeBuy()) {
            this.spinner.show();
            this.stockTransactionService.buy(this.selectedStockToBuy.Id, this.buyingQty, this.turnId, this.bankAccountId)
                .subscribe((data: any) => {
                    this.getSellingItem();
                    this.isSelectItemToBuy = false;
                    this.selectedStockToBuy = new Stock();
                    this.buyingQty = 0;
                    this.spinner.hide();
                },
                (error: Response) => {
                    this.spinner.hide();
                });
        }
    }

    getHistory(): void {
        this.spinner.show();
        this.stockTransactionService.history(this.roundId, this.bankAccountId)
            .subscribe((data: StockTransactionFull[]) => {
                this.transactionHistoryList = data;
                this.getSellingItem();
                this.isSelectItemToSell = false;
                this.selectedSellStockTransaction = new StockTransactionFull();
                this.sellingQty = 0;
                this.sellingPrice = 0;
                this.spinner.hide();
            },
            (error: Response) => {
                this.spinner.hide();
            });

    }

    onChangeSellingRowChecked(items: StockTransactionFull) {
        this.sellingItemList.forEach(function (v, k) {
            if (items.Id != v.Id || v.IsCheck === undefined || v.IsCheck === null)
                v.IsCheck = false;
            });
        if (items.IsCheck) {
            this.isSelectItemToSell = true;
            this.selectedSellStockTransaction = items;
        }
        else {
            this.isSelectItemToSell = false;
            this.selectedSellStockTransaction = new StockTransactionFull();
        }
            
    }

    onChangeBuyingRowChecked(items: Stock) {
        this.buyingItemList.forEach(function (v, k) {
            if (items.Id != v.Id || v.IsCheck === undefined || v.IsCheck === null)
                v.IsCheck = false;
        });

        if (items.IsCheck) {
            this.isSelectItemToBuy = true;
            this.selectedStockToBuy = items;
        }
        else {
            this.isSelectItemToBuy = false;
            this.selectedStockToBuy = new Stock();
        }
    }

    view: any[] = [700, 400];

    // options
    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    showXAxisLabel = true;
    xAxisLabel = 'Year';
    showYAxisLabel = true;
    yAxisLabel = 'Price';

    colorScheme = {
        domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
    };

    // line, area
    autoScale = true;

    onSelect(event) {
        console.log(event);
    }

}
