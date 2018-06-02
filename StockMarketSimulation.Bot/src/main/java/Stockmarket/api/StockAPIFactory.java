/**
 * 
 */
package Stockmarket.api;

import Stockmarket.api.impl.CommonStockAPI;
import Stockmarket.api.impl.PreferredStockAPI;
import Stockmarket.service.IStockDataService;
import Stockmarket.types.StockType;

/**
 * @author Shivakumar Ramannavar
 *
 */
public class StockAPIFactory {

	/**
	 * 
	 */
	private StockAPIFactory() {
		
	}

	public static IStockAPI generateStockAPI(StockType stockType, IStockDataService stockDataService) {

		switch (stockType) {
			case COMMON:
				return new CommonStockAPI(stockDataService);
	
			case PREFERRED:
				return new PreferredStockAPI(stockDataService);
	
			default:
				break;
		}

		return null;
	}

}
