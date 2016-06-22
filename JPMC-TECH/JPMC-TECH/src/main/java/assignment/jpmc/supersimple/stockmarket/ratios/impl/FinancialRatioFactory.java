package assignment.jpmc.supersimple.stockmarket.ratios.impl;

import assignment.jpmc.supersimple.stockmarket.ratios.FinancialRatio;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioType;

public final class FinancialRatioFactory
{
    private FinancialRatioFactory()
    {
    }

    /**
     * This is a factory method returning FinancialRatio based on a given FinancialRatioType
     * 
     * @param FinancialRatioType
     * @return FinancialRatio
     */
    public static FinancialRatio getCalculator( FinancialRatioType type )
    {
        FinancialRatio calculator;
        switch ( type )
        {

            case DIVIDEND_YIELD:
                calculator = new DividendYield();
                break;
            case PRICE_EARNING_RATION:
                calculator = new PriceEarningsRatio();
                break;
            case VOLUME_WEIGHTED_STOCK_PRICE:
                calculator = new VolumeWeightedStockPrice();
                break;
            default:
                throw new RuntimeException( "Ratio not found." );
        }

        return calculator;
    }
}
