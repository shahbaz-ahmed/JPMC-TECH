package assignment.jpmc.supersimple.stockmarket.ratios;

import java.math.BigDecimal;

import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioContext;

public interface FinancialRatio
{
    BigDecimal calculate( FinancialRatioContext context )
        throws StockException, InvalidPriceException;
}
