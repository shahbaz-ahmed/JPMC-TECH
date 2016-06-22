package assignment.jpmc.supersimple.stockmarket.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GBCEUtilTest
{

    @Before
    public void setUp()
    {

    }

    @Test
    public void test_calculateNthRoot()
    {

        BigDecimal value = GBCEUtils.calculateNthRoot( 6, new BigDecimal( "15625" ), new BigDecimal( "0.001" ) );
        value = value.setScale( 0, BigDecimal.ROUND_HALF_EVEN );
        Assert.assertEquals( new BigDecimal( "5" ), value );
    }
}
