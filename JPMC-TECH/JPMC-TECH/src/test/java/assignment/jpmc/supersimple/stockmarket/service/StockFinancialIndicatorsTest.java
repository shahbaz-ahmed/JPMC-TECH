package assignment.jpmc.supersimple.stockmarket.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.StockType;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeType;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.service.impl.FinancialCalculationsServiceImpl;

public class StockFinancialIndicatorsTest
{

    private FinancialCalculationsServiceImpl service;

    public final static int ROUNDING_SCALE = 2;

    @Before
    public void setUp()
    {
        this.service = new FinancialCalculationsServiceImpl( getDefaultStocks() );
    }

    @Test
    public void test_calculateDividendYield_Common()
    {
        BigDecimal value = null;
        try
        {
            value = service.calculateDividendYield( "ALE", new BigDecimal( 24 ) );
        }
        catch ( StockException e )
        {
            Assert.fail();
        }
        catch ( InvalidPriceException e )
        {
            Assert.fail();
        }
        BigDecimal toTest = new BigDecimal( 0.96 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );
    }

    @Test
    public void test_calculateDividendYield_Preferred()
    {
        BigDecimal value = null;
        try
        {
            value = service.calculateDividendYield( "GIN", new BigDecimal( 200 ) );
        }
        catch ( StockException e )
        {
            Assert.fail();
        }
        catch ( InvalidPriceException e )
        {
            Assert.fail();
        }
        BigDecimal toTest = new BigDecimal( 0.01 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );
    }

    @Test( expected = InvalidPriceException.class )
    public void test_calculateDividendYield_WrongPrice()
        throws InvalidPriceException
    {
        BigDecimal value = null;
        try
        {
            value = service.calculateDividendYield( "ALE", new BigDecimal( -24 ) );
        }
        catch ( StockException e )
        {
            Assert.fail();
        }

        Assert.assertNull( value );
    }

    @Test
    public void test_recordTrade()
    {
        Boolean value = false;
        try
        {
            value = service.recordTrade( "TEA", new Date(), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 12 ) );
        }
        catch ( StockException e )
        {
            Assert.fail();
        }

        Assert.assertTrue( value );
    }

    @Test
    public void test_calculatePERatio_PositiveValue()
    {
        BigDecimal value = null;
        try
        {
            value = service.calculatePERatio( "ALE", new BigDecimal( 24 ) );
        }
        catch ( StockException e )
        {
            Assert.fail();
        }
        catch ( InvalidPriceException e )
        {
            Assert.fail();
        }

        BigDecimal toTest = new BigDecimal( 1.04 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );
    }

    @Test
    public void test_calculateVolumeWeightedStockPrice_5minORLess()
    {
        BigDecimal value = null;
        try
        {
            recordDefaultTrades();
            value = service.calculateVolumeWeightedStockPrice( 5 );
        }
        catch ( StockException e )
        {
            Assert.fail();
        }
        catch ( InvalidPriceException e )
        {
            Assert.fail();
        }

        BigDecimal toTest = new BigDecimal( 249.42 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );

    }

    @Test
    public void test_calculateGBCEAllShareIndexUsingGM()
    {
        BigDecimal value = null;
        try
        {
            recordDefaultTrades();
            value = service.calculateGBCEAllShareIndexUsingGM();
        }
        catch ( StockException e )
        {
            Assert.fail();
        }
        catch ( InvalidPriceException e )
        {
            Assert.fail();
        }

        BigDecimal toTest = new BigDecimal( 162.39 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );
    }

    @Test( expected = InvalidPriceException.class )
    public void test_calculateDividendYield_Null_Value()
        throws InvalidPriceException, StockException
    {

        service.calculateDividendYield( "ALE", null );

    }

    private void recordDefaultTrades()
        throws StockException
    {
        service.recordTrade( "TEA", getTimestamp( 1 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 120 ) );
        service.recordTrade( "TEA", getTimestamp( 4 ), new BigInteger( "3" ), TradeType.BUY, new BigDecimal( 130 ) );
        service.recordTrade( "TEA", getTimestamp( 20 ), new BigInteger( "1" ), TradeType.BUY, new BigDecimal( 90 ) );
        service.recordTrade( "TEA", getTimestamp( 100 ), new BigInteger( "4" ), TradeType.BUY, new BigDecimal( 125 ) );
        service.recordTrade( "TEA", getTimestamp( 3 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 150 ) );

        service.recordTrade( "POP", getTimestamp( 4 ), new BigInteger( "1" ), TradeType.BUY, new BigDecimal( 120 ) );
        service.recordTrade( "POP", getTimestamp( 7 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 125 ) );
        service.recordTrade( "POP", getTimestamp( 1 ), new BigInteger( "5" ), TradeType.BUY, new BigDecimal( 150 ) );

        service.recordTrade( "ALE", getTimestamp( 15 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 80 ) );
        service.recordTrade( "ALE", getTimestamp( 2 ), new BigInteger( "5" ), TradeType.BUY, new BigDecimal( 85 ) );
        service.recordTrade( "ALE", getTimestamp( 55 ), new BigInteger( "6" ), TradeType.BUY, new BigDecimal( 90 ) );
        service.recordTrade( "ALE", getTimestamp( 88 ), new BigInteger( "3" ), TradeType.BUY, new BigDecimal( 50 ) );

        service.recordTrade( "GIN", getTimestamp( 109 ), new BigInteger( "8" ), TradeType.BUY, new BigDecimal( 200 ) );
        service.recordTrade( "GIN", getTimestamp( 2 ), new BigInteger( "12" ), TradeType.BUY, new BigDecimal( 300 ) );

        service.recordTrade( "JOE", getTimestamp( 107 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 400 ) );
        service.recordTrade( "JOE", getTimestamp( 1 ), new BigInteger( "4" ), TradeType.BUY, new BigDecimal( 500 ) );
        service.recordTrade( "JOE", getTimestamp( 3 ), new BigInteger( "7" ), TradeType.BUY, new BigDecimal( 300 ) );
        service.recordTrade( "JOE", getTimestamp( 7 ), new BigInteger( "8" ), TradeType.BUY, new BigDecimal( 260 ) );
        service.recordTrade( "JOE", getTimestamp( 77 ), new BigInteger( "9" ), TradeType.BUY, new BigDecimal( 200 ) );
        service.recordTrade( "JOE", getTimestamp( 1 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 400 ) );
        service.recordTrade( "JOE", getTimestamp( 109 ), new BigInteger( "6" ), TradeType.BUY, new BigDecimal( 350 ) );

    }

    private static Map<String, StockDBO> getDefaultStocks()
    {
        Map<String, StockDBO> stocks = new HashMap<String, StockDBO>();
        stocks.put( "TEA", new StockDBO( "TEA", StockType.COMMON, BigDecimal.ZERO, null, new BigDecimal( 100 ),
                                         new ArrayList<TradeDBO>() ) );
        stocks.put( "POP", new StockDBO( "POP", StockType.COMMON, new BigDecimal( 8 ), null, new BigDecimal( 100 ),
                                         new ArrayList<TradeDBO>() ) );
        stocks.put( "ALE", new StockDBO( "ALE", StockType.COMMON, new BigDecimal( 23 ), null, new BigDecimal( 60 ),
                                         new ArrayList<TradeDBO>() ) );
        stocks.put( "GIN", new StockDBO( "GIN", StockType.PREFFERED, new BigDecimal( 8 ), new BigDecimal( 0.02 ),
                                         new BigDecimal( 100 ), new ArrayList<TradeDBO>() ) );
        stocks.put( "JOE", new StockDBO( "JOE", StockType.COMMON, new BigDecimal( 13 ), null, new BigDecimal( 250 ),
                                         new ArrayList<TradeDBO>() ) );
        return stocks;
    }

    private Date getTimestamp( int minFromNow )
    {
        Calendar c = Calendar.getInstance();
        c.add( Calendar.MINUTE, ( minFromNow ) * ( -1 ) );
        return c.getTime();
    }
}
