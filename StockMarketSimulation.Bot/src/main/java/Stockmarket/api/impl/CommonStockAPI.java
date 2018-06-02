/**
 * 
 */
package Stockmarket.api.impl;

import Stockmarket.domain.Stock;
import Stockmarket.exception.InvalidStockException;
import Stockmarket.service.IStockDataService;
import Stockmarket.service.Logger;

/**
 * @author sramanna
 *
 */
public class CommonStockAPI extends AbstractStockAPI {

	public CommonStockAPI(IStockDataService stockDataService) {
		super(stockDataService);
	}

	/* (non-Javadoc)
	 * @see com.app.stockmarket.api.impl.AbstractStockAPI#calculateDividendYield(java.lang.String, double)
	 */
	@Override
	public double calculateDividendYield(String stockSymbol, double price) throws InvalidStockException {
		Stock stock = stockDataService.getStockData(stockSymbol);
		
		double dividendYield = 0.0;

		Logger.logDebugMessage("dividendYield  =" + stock.getLastDividend() + "/" + price);
		if (price != 0.0) {
			dividendYield = stock.getLastDividend() / price;
		}
		
		Logger.logDebugMessage("               =" + dividendYield + "\n");
		return dividendYield;
	}

}
