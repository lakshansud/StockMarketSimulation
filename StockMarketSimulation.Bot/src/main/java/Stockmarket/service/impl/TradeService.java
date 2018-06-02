/**
 * 
 */
package Stockmarket.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import Stockmarket.domain.Stock;
import Stockmarket.domain.TradeTransaction;
import Stockmarket.exception.InvalidStockException;
import Stockmarket.service.IStockDataService;
import Stockmarket.service.ITradeService;
import Stockmarket.service.Logger;
import Stockmarket.service.ITradeService.BuySellIndicator;

/**
 * @author sramanna
 *
 */
public class TradeService implements ITradeService {
	
	private IStockDataService stockDS;

	public TradeService(IStockDataService stockDS) {
		super();
		this.stockDS = stockDS;
	}

	/* (non-Javadoc)
	 * @see com.app.stockmarket.service.ITradeService#createStockInMarket(com.app.stockmarket.domain.Stock)
	 */
	@Override
	public boolean createStockInMarket(Stock stock) throws InvalidStockException {
		stockDS.saveStockData(stock);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.app.stockmarket.service.ITradeService#tradeStockInMarket(java.lang.String, int, com.app.stockmarket.service.ITradeService.BuySellIndicator, double, java.util.Date)
	 */
	@Override
	public boolean tradeStockInMarket(String stockSymbol, int quantity, BuySellIndicator buySellIndicator,
			double tradedPrice, Date timestamp) throws InvalidStockException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setStockSymbol(stockSymbol);
		tradeTransaction.setBuySellIndicator(buySellIndicator);
		tradeTransaction.setQuantity(quantity);
		tradeTransaction.setTimestamp(timestamp);
		tradeTransaction.setTradedPrice(tradedPrice);
		stockDS.recordTradeTransation(tradeTransaction);
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		
		if (buySellIndicator == BuySellIndicator.BUY) {
			Logger.logDebugMessage("Transaction completed for BUY " + stockSymbol + " Stock for $ " +  tradedPrice + " at  "+ dt1.format(timestamp));
		} else {
			Logger.logDebugMessage("Transaction completed for SELL " + stockSymbol + " Stock for $ " +  tradedPrice + " at "+ dt1.format(timestamp));
		}
		
		return true;
	}

	@Override
	public Stock getStockData(String stockSymbol) throws InvalidStockException {
		return stockDS.getStockData(stockSymbol);
	}

}
