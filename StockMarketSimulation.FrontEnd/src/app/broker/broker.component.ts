import { Component,OnInit } from '@angular/core';
import { StockTransaction,StockTransactionFull } from '../models/stocktransaction';
import { Stock } from '../models/stock';
import { Sector } from '../models/sector';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';

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
    transactionHistoryList: StockTransaction[] = new Array<StockTransaction>();
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
    constructor(private fb: FormBuilder, private bankAccountService: BankAccountService, private brokerService: BrokerService, private stockTransactionService: StockTransactionService, private sectorService: SectorService, private stockService: StockService) {

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
            debugger;
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
            "value": 7200000
        }
    ];

    multi = [
        {
            "name": "ACER",
            "series": [
                {
                    "name": "2010",
                    "value": 7300000
                },
                {
                    "name": "2011",
                    "value": 8940000
                }
            ]
        },

        {
            "name": "HP",
            "series": [
                {
                    "name": "2010",
                    "value": 7870000
                },
                {
                    "name": "2011",
                    "value": 8270000
                }
            ]
        },

        {
            "name": "DELL",
            "series": [
                {
                    "name": "2010",
                    "value": 5000002
                },
                {
                    "name": "2011",
                    "value": 5800000
                }
            ]
        }
    ];

    getSellingItem(): void {
        this.stockTransactionService.getSellingItem(1,1)
            .subscribe((data: StockTransactionFull[]) => {
                this.sellingItemList = data;
            },
            (error: Response) => {
            });
    }

    start(): void {
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
            },
            (error: Response) => {
            });
    }

    getSectors(): void {
        this.sectorService.getAll()
            .subscribe((data: Sector[]) => {
                this.sectorList = data;
            },
            (error: Response) => {
            });
    }

    getStockBySectorId(selectedBuySectorId:number): void {
        this.stockService.getBtSectorId(selectedBuySectorId)
            .subscribe((data: Stock[]) => {
                this.buyingItemList = data;
            },
            (error: Response) => {
            });
    }

    getCurrentUserInfo(userId: number): void {
        this.bankAccountService.getCurrentUserInfo(userId)
            .subscribe((data: CurrentBankInfo) => {
                this.currentBankInfo = data;
            },
            (error: Response) => {
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
            this.stockTransactionService.sell(this.selectedSellStockTransaction.Id, this.sellingQty, this.sellingPrice, this.turnId, this.bankAccountId)
                .subscribe((data: any) => {
                    debugger;
                    this.getSellingItem();
                    this.isSelectItemToSell = false;
                    this.selectedSellStockTransaction = new StockTransactionFull();
                    this.sellingQty = 0;
                    this.sellingPrice = 0;
                },
                (error: Response) => {
                    debugger;
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
            this.stockTransactionService.buy(this.selectedStockToBuy.Id, this.buyingQty, this.turnId, this.bankAccountId)
                .subscribe((data: any) => {
                    this.getSellingItem();
                    this.isSelectItemToBuy = false;
                    this.selectedStockToBuy = new Stock();
                    this.buyingQty = 0;
                },
                (error: Response) => {
                });
        }
    }

    getHistory(): void {
        if (this.validateBeforeBuy()) {
            this.stockTransactionService.history(this.roundId, this.bankAccountId)
                .subscribe((data: StockTransaction[]) => {
                    this.transactionHistoryList = data;
                    this.getSellingItem();
                    this.isSelectItemToSell = false;
                    this.selectedSellStockTransaction = new StockTransactionFull();
                    this.sellingQty = 0;
                    this.sellingPrice = 0;
                },
                (error: Response) => {
                });
        }
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
    xAxisLabel = 'Country';
    showYAxisLabel = true;
    yAxisLabel = 'Population';

    colorScheme = {
        domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
    };

    // line, area
    autoScale = true;

    onSelect(event) {
        console.log(event);
    }

}
