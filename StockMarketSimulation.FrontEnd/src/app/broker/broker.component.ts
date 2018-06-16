import { Component,OnInit } from '@angular/core';
import { StockTransaction,StockTransactionFull } from '../models/stocktransaction';
import { Stock } from '../models/stock';
import { Sector } from '../models/sector';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';

import { StockTransactionService } from '../shared/services/stocktransaction.service';
import { SectorService } from '../shared/services/sector.service';
import { StockService } from '../shared/services/stock.service';

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
    sellingPrice: number = 0;
    buyingQty: number = 0;

    constructor(private fb: FormBuilder, private stockTransactionService: StockTransactionService, private sectorService: SectorService, private stockService: StockService) {

    }

    ngOnInit() {
        this.sellForm = this.fb.group({
            qty: ['', [Validators.required as any]],
            price: ['', [Validators.required as any]],
          
        });
        this.getSellingItem();
        this.getSectors();
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
