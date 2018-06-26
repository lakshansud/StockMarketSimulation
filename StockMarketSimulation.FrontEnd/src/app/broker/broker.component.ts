import { Component,OnInit } from '@angular/core';
import { StockTransaction,StockTransactionFull } from '../models/stocktransaction';
import { Stock } from '../models/stock';
import { ValueChangeForYears } from '../models/stockpricehistory';
import { Sector } from '../models/sector';
import { Turn } from '../models/turn';
import { Marks } from '../models/marks';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { StockTransactionService } from '../shared/services/stocktransaction.service';
import { SectorService } from '../shared/services/sector.service';
import { StockService } from '../shared/services/stock.service';
import { BrokerService } from '../shared/services/broker.service';
import {SecurityService  } from '../shared/services/security.service';
import { BankAccountService } from '../shared/services/bankaccount.service';
import { Broker, StartResponce } from '../models/broker';
import { CurrentBankInfo } from '../models/bankaccount';
import { Overlay } from 'ngx-modialog';
import { Modal } from 'ngx-modialog/plugins/bootstrap';

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
    marks: Marks[] = new Array<Marks>();
    sellingQty: number = 0;
    sellingQtyError: string;
    sellingPrice: number = 0;
    sellingPriceError: string;
    buyingQty: number = 0;
    buyingError: string;
    error = "";
    turnId: number = 0;
    roundId: number = 0;
    turn: number = 0;
    round: number = 0;
    today = new Date().toLocaleTimeString();
    isPlayForTurn: boolean = false;
    currentBankInfo: CurrentBankInfo = new CurrentBankInfo();
    bankAccountId: number = 0;
    constructor(public modal: Modal, public ngxSmartModalService: NgxSmartModalService, private securityService:SecurityService, private fb: FormBuilder, private spinner: NgxSpinnerService, private bankAccountService: BankAccountService, private brokerService: BrokerService, private stockTransactionService: StockTransactionService, private sectorService: SectorService, private stockService: StockService) {

    }

    onClick() {

        const dialogRef = this.modal.alert()
            .size('lg')
            .showClose(false)
            .title('Final marks')
            .body(`
           <div class="table-responsive"  style="overflow-y:auto;height:300px">
                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>Stock Name</th>
                                    <th>Balance</th>
                                    <th>Position</th>
                                  
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>Stock Name</th>
                                    <th>Balance</th>
                                    <th>Position</th>
                                </tr>
                            </tfoot>
                            <tbody>
                                <tr *ngFor="let mark of marks; let rowIndex=index">
                                    <td [innerHTML]="mark.Name"></td>
                                    <td [innerHTML]="mark.CurrentBankAmount"></td>
                                    <td [innerHTML]="mark.Position" class="text-right"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>`)
            .open();

    }

    ngOnInit() {
        this.sellForm = this.fb.group({
            qty: ['', [Validators.required as any]],
            price: ['', [Validators.required as any]],
        });
     
        var turn = localStorage.getItem('Turn');
        var round = localStorage.getItem('round');
        if (turn)
            this.turn = +turn;

        if (round)
            this.round = +round;

        var turnId = localStorage.getItem('TurnId');
        if (turnId)
            this.turnId = +turnId;

        var round = localStorage.getItem('roundId');
        if (round)
            this.roundId = +round;
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

        setInterval(() => {
            console.log("Turn is going to refresh...");
            this.getCurrentTurn();
        }, 5000);
        this.getSellingItem();
        this.getSectors();
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
        this.stockTransactionService.getSellingItem(this.bankAccountId, this.roundId)
            .subscribe((data: StockTransactionFull[]) => {
                this.sellingItemList = data;
                this.spinner.hide();
            },
            (error: Response) => {

                this.spinner.hide();
            });
    }

    getMarks(): void {
        this.spinner.show();
        this.bankAccountService.GetUsersMarks(this.roundId)
            .subscribe((data: Marks[]) => {
                this.marks = data.sort(x => x.CurrentBankAmount);
                var i = 0;
                this.marks.forEach((res) => {
                    i++;
                    res.Position = i;
                });
                this.onClick();
                setTimeout(function () {
                    this.securityService.logout();
                }.bind(this), 10000);
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

    getCurrentTurn(): void {
        this.brokerService.getCurrentTurn()
            .subscribe((data: Turn) => {
                if (data.Turn == 30) {
                    this.getMarks();
                }

                if (this.turn < data.Turn) {
                    this.isPlayForTurn = false;
                    this.turnId = data.Id;
                    this.turn = data.Turn
                } 
            },
            (error: Response) => {

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
        if (this.isPlayForTurn) {
            this.sellingQtyError = "You can take one action for a turn, Please wait for next turn.";
            return false;
        }
        return true;
    }

    sell(): void {
        if (this.validBefore()) {
            this.spinner.show();
            this.stockTransactionService.sell(this.selectedSellStockTransaction.Id, this.sellingQty, this.sellingPrice, this.turnId, this.bankAccountId)
                .subscribe((data: any) => {
                    this.isPlayForTurn = true;
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

        if (this.isPlayForTurn) {
            this.buyingError = "You can take ont action for a turn, Please wait for next turn.";
            return false;
        }
        return true;
    }

    buy(): void {
        if (this.validateBeforeBuy()) {
            this.spinner.show();
            this.stockTransactionService.buy(this.selectedStockToBuy.Id, this.buyingQty, this.turnId, this.bankAccountId)
                .subscribe((data: any) => {
                    this.isPlayForTurn = true;
                    this.getSellingItem();
                    this.getHistory();
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
