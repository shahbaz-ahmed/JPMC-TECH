package assignment.jpmc.supersimple.stockmarket.ratios.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.ratios.FinancialRatio;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioContext;

public class PriceEarningsRatio implements FinancialRatio {

	private static final Logger LOGGER = Logger.getLogger(PriceEarningsRatio.class
			.getName());
	
	 /**
     * This method calculates Price Earnings Ratio for a stock with given price.
     * 
     * @param FinancialRatioContext is a generalised parameter required
     * to carry the required parameters for different ratios.
     * @return BigDecimal as value of the ratio
     */
	public BigDecimal calculate(FinancialRatioContext context)
			throws StockException, InvalidPriceException {
		BigDecimal price = context.getPrice();
		StockDBO stock = context.getFirstStock();
		BigDecimal result = price.divide(stock.getLastDividend(),  MathContext.DECIMAL128);
		LOGGER.log(Level.FINE, "PriceEarningsRatio for " + stock.getStockSymbol() + " is " + result);
		return result;
	}

}
