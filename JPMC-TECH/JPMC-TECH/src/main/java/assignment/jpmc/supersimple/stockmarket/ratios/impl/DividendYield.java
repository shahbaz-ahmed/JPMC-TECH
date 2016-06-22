package assignment.jpmc.supersimple.stockmarket.ratios.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.StockType;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.ratios.FinancialRatio;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioContext;

public class DividendYield
    implements FinancialRatio
{
    private static final Logger LOGGER = Logger.getLogger( DividendYield.class.getName() );

    /**
     * This method calculates Dividend Yield for a stock with given price.
     * 
     * @param FinancialRatioContext is a generalised parameter required
     * to carry the required parameters for different ratios.
     * @return BigDecimal as value of the ratio
     */
    public BigDecimal calculate( FinancialRatioContext context )
        throws StockException, InvalidPriceException
    {
        BigDecimal price = context.getPrice();
        StockDBO stock = context.getFirstStock();
        BigDecimal value = null;
        if ( stock.getStockType() == StockType.COMMON )
        {
            value = stock.getLastDividend().divide( price, MathContext.DECIMAL128 );
        }
        else if ( stock.getStockType() == StockType.PREFFERED )
        {
            value =
                stock.getFixedDividend().multiply( stock.getParValue(), MathContext.DECIMAL128 ).divide( price,
                                                                                                         MathContext.DECIMAL128 );
        }
        LOGGER.log( Level.FINE, "DividendYield for " + stock.getStockSymbol() + " is " + value );
        return value;
    }

}
