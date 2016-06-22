package assignment.jpmc.supersimple.stockmarket.util;

import java.math.BigDecimal;
import java.math.MathContext;

public final class GBCEUtils
{

    private GBCEUtils()
    {

    }

    /**
     * calculates nth root of given a value to a given precision
     * 
     * @param int root, BigDecimal value, BigDecimal per
     * @return BigDecimal
     */
    public static BigDecimal calculateNthRoot( int root, BigDecimal value, BigDecimal per )
    {
        BigDecimal xFactor = value.divide( value );
        BigDecimal yFactor = BigDecimal.ZERO;
        do
        {
            yFactor = xFactor;
            xFactor =
                xFactor.add( value.subtract( xFactor.pow( root ) ).divide( new BigDecimal( root ).multiply( xFactor.pow( root - 1 ) ),
                                                                           MathContext.DECIMAL128 ) );
        }
        while ( xFactor.subtract( yFactor ).abs().compareTo( per ) > 0 );
        return xFactor;
    }

}
