function test() {

var config = {
	predictionSteps : 1,
	step : 1,
	serie : [
		1,2,1,2,
		1,5,1,3,
		2,2,2,7,
		4,2,9,2,
		1,5,2,8,
	]  
}
var prediction = brain_predict.predict(config) ;
console.log("Prediction Value\n" + prediction.prediction + "\n");
}

test();