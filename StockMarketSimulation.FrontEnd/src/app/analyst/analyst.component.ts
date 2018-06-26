import { Component, OnInit } from '@angular/core';
import { StockService } from '../shared/services/stock.service';
import { Stock, AnalystModel } from '../models/stock';
declare var brain_predict: any;

@Component({
    selector: 'analyst',
    templateUrl: './analyst.component.html'
})
export class AnalystComponent implements OnInit{
    title = 'app';
    today = new Date().toLocaleTimeString();
    analystModel: AnalystModel[] = new Array<AnalystModel>();
    buyingStock: AnalystModel[] = new Array<AnalystModel>();
    sellingStock: AnalystModel[] = new Array<AnalystModel>();
    
	private config = {
		predictionSteps : 1,
		step : 1,
		serie : [ ]  
	}

    constructor(private stockService: StockService) {

	}
  
    ngOnInit() {
         this.getDataForPredicate()
    }

     getDataForPredicate(): void {
         this.stockService.getDataForPredicate()
             .subscribe((data: AnalystModel[]) => {
                 for (var i = 0; i < data.length; i++) {
                     var obj = data[i];

                     this.config.serie = obj.valus;
                     var prediction = brain_predict.predict(this.config);
                     obj.PredictPrice = prediction.prediction[0];
                     if (obj.CurrentPrice - 50 > obj.PredictPrice) {
                         var stock = new AnalystModel();
                         stock.Name = obj.Name;
                         stock.PredictPrice = obj.PredictPrice;
                         this.sellingStock.push(stock);

                     } else {
                         var stock = new AnalystModel();
                         stock.Name = obj.Name;
                         stock.PredictPrice = obj.PredictPrice;
                         if (stock.PredictPrice.toString() == "NaN")
                            obj.valus.forEach((res)=>{ stock.PredictPrice = res });

                         this.buyingStock.push(stock);
                     }
                 }
                
                 this.analystModel = data;

             },
             (error: Response) => {
             });
     }
}
