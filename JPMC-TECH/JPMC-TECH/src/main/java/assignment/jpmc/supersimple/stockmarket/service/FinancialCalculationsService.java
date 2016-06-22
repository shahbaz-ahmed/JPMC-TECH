package assignment.jpmc.supersimple.stockmarket.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import assignment.jpmc.supersimple.stockmarket.dbo.TradeType;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public interface FinancialCalculationsService
{
    BigDecimal calculateDividendYield( String symbol, BigDecimal price )
        throws StockException, InvalidPriceException;

    BigDecimal calculatePERatio( String symbol, BigDecimal price )
        throws StockException, InvalidPriceException;

    Boolean recordTrade( String symbol, Date timestamp, BigInteger quantity, TradeType tradeType, BigDecimal price )
        throws StockException;

    BigDecimal calculateVolumeWeightedStockPrice( int minutes )
        throws StockException, InvalidPriceException;

    BigDecimal calculateGBCEAllShareIndexUsingGM()
        throws StockException, InvalidPriceException;

}
