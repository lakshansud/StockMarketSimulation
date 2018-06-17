import { Component } from '@angular/core';

@Component({
    selector: 'analyst',
    templateUrl: './analyst.component.html'
})
export class AnalystComponent {
  title = 'app';

	private config = {
		predictionSteps : 1,
		step : 1,
		serie : [
			1,2,1,2,
			5,5,3,5
		]  
	}

  constructor() {

	var prediction = brain_predict.predict(this.config) ;
		console.log("Prediction Value\n" + prediction.prediction + "\n");
	}
  

}
