import { Component,OnInit } from '@angular/core';
import { StockTransaction,StockTransactionFull } from '../models/stocktransaction';
import { Stock } from '../models/stock';

import { StockTransactionService } from '../shared/services/stocktransaction.service';

@Component({
    selector: 'broker',
    templateUrl: './broker.component.html'
})
export class BrokerComponent implements OnInit {
    sellingItemList: StockTransactionFull[] = new Array<StockTransactionFull>();
    buyingItemList: Stock[] = new Array<Stock>();
    transactionHistoryList: StockTransactionFull[] = new Array<StockTransactionFull>();

    constructor(private stockTransactionService: StockTransactionService) {

    }

    ngOnInit() {
        this.getSellingItem();
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

    onChangeSellingRowChecked(items: StockTransaction) {
        this.sellingItemList.forEach(function (v, k) {
            if (items.Id != v.Id || v.IsCheck === undefined || v.IsCheck === null)
                v.IsCheck = false;
            });
     
    }

    onChangeBuyingRowChecked(items: Stock) {
        this.buyingItemList.forEach(function (v, k) {
            if (items.Id != v.Id || v.IsCheck === undefined || v.IsCheck === null)
                v.IsCheck = false;
        });

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
