package assignment.jpmc.supersimple.stockmarket.ratios.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.ratios.FinancialRatio;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioContext;

public class VolumeWeightedStockPrice
    implements FinancialRatio
{

    private static final Logger LOGGER = Logger.getLogger( VolumeWeightedStockPrice.class.getName() );

    /**
     * This method calculates Volume Weighted Stock Price for a stock with given price.
     * 
     * @param FinancialRatioContext is a generalised parameter required
     * to carry the required parameters for different ratios.
     * @return BigDecimal as value of the ratio
     */
    public BigDecimal calculate( FinancialRatioContext context )
        throws StockException, InvalidPriceException
    {

        if ( context.getTrades().isEmpty() )
        {
            LOGGER.log( Level.WARNING, "No Trade found." );
            return null;
        }

        BigDecimal priceSum = BigDecimal.ZERO;
        BigInteger quantitySum = BigInteger.ZERO;

        for ( TradeDBO tradeDBO : context.getTrades() )
        {
            BigDecimal price = tradeDBO.getPrice();
            BigDecimal quatity = new BigDecimal( tradeDBO.getQuantity() );
            priceSum = priceSum.add( price.multiply( quatity, MathContext.DECIMAL128 ) );
            quantitySum = quantitySum.add( tradeDBO.getQuantity() );
        }
        BigDecimal value = priceSum.divide( new BigDecimal( quantitySum ), MathContext.DECIMAL128 );
        LOGGER.log( Level.INFO, "VolumeWeightedStockPrice is " + value );
        return value;
    }

}
