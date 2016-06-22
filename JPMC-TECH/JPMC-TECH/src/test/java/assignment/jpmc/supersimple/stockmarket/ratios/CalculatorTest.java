package assignment.jpmc.supersimple.stockmarket.ratios;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.StockType;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeType;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.ratios.impl.FinancialRatioFactory;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioContext;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioType;

public class CalculatorTest
{
    FinancialRatio dividendYield;

    FinancialRatio priceEarningsRatio;

    FinancialRatio volumeWeightedStockPrice;

    public final static int ROUNDING_SCALE = 2;

    @Before
    public void setUp()
        throws StockException
    {
        dividendYield = FinancialRatioFactory.getCalculator( FinancialRatioType.DIVIDEND_YIELD );
        priceEarningsRatio = FinancialRatioFactory.getCalculator( FinancialRatioType.PRICE_EARNING_RATION );
        volumeWeightedStockPrice = FinancialRatioFactory.getCalculator( FinancialRatioType.VOLUME_WEIGHTED_STOCK_PRICE );
    }

    @Test
    public void test_DividendYield_Common()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( new BigDecimal( "80" ) );
        StockDBO stockDbo =
            new StockDBO( "ALE", StockType.COMMON, new BigDecimal( 23 ), null, new BigDecimal( 60 ),
                          new ArrayList<TradeDBO>() );
        List<StockDBO> stockDbos = new ArrayList<StockDBO>();
        stockDbos.add( stockDbo );
        context.setStocks( stockDbos );

        BigDecimal value = dividendYield.calculate( context );
        value = value.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        BigDecimal toTest = new BigDecimal( 0.29 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );

    }

    @Test( expected = InvalidPriceException.class )
    public void test_DividendYield_InvalidPrice()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( new BigDecimal( "-80" ) );
        StockDBO stockDbo =
            new StockDBO( "ALE", StockType.COMMON, new BigDecimal( 23 ), null, new BigDecimal( 60 ),
                          new ArrayList<TradeDBO>() );
        List<StockDBO> stockDbos = new ArrayList<StockDBO>();
        stockDbos.add( stockDbo );
        context.setStocks( stockDbos );

        dividendYield.calculate( context );

    }

    @Test( expected = InvalidPriceException.class )
    public void test_DividendYield_Nostock()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( new BigDecimal( "-80" ) );
        dividendYield.calculate( context );

    }

    @Test
    public void test_DividendYield_Preffered()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( new BigDecimal( "80" ) );
        StockDBO stockDbo =
            new StockDBO( "GIN", StockType.PREFFERED, new BigDecimal( 8 ), new BigDecimal( 0.02 ),
                          new BigDecimal( 100 ), new ArrayList<TradeDBO>() );
        List<StockDBO> stockDbos = new ArrayList<StockDBO>();
        stockDbos.add( stockDbo );
        context.setStocks( stockDbos );

        BigDecimal value = dividendYield.calculate( context );
        value = value.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        BigDecimal toTest = new BigDecimal( 0.03 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );

    }

    @Test
    public void test_PriceEarningsRatio()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( new BigDecimal( "80" ) );
        StockDBO stockDbo =
            new StockDBO( "GIN", StockType.PREFFERED, new BigDecimal( 8 ), new BigDecimal( 0.02 ),
                          new BigDecimal( 100 ), new ArrayList<TradeDBO>() );
        List<StockDBO> stockDbos = new ArrayList<StockDBO>();
        stockDbos.add( stockDbo );
        context.setStocks( stockDbos );

        BigDecimal value = priceEarningsRatio.calculate( context );
        value = value.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        BigDecimal toTest = new BigDecimal( 10.00 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );

    }

    @Test
    public void test_volumeWeightedStockPrice()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setTrades( getDefaultTrades() );

        BigDecimal value = volumeWeightedStockPrice.calculate( context );
        value = value.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        BigDecimal toTest = new BigDecimal( 127.50 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );

    }

    @Test( expected = NullPointerException.class )
    public void test_volumeWeightedStockPrice_NoTrade()
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();

        BigDecimal value = volumeWeightedStockPrice.calculate( context );
        value = value.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        BigDecimal toTest = new BigDecimal( 127.50 );
        toTest = toTest.setScale( ROUNDING_SCALE, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, value );

    }

    private List<TradeDBO> getDefaultTrades()
    {

        List<TradeDBO> list = new ArrayList<TradeDBO>();
        list.add( new TradeDBO( "TEA", getTimestamp( 10 ), new BigInteger( "2" ), TradeType.BUY, new BigDecimal( 150 ) ) );
        list.add( new TradeDBO( "TEA", getTimestamp( 4 ), new BigInteger( "6" ), TradeType.BUY, new BigDecimal( 120 ) ) );

        return list;

    }

    private Date getTimestamp( int minFromNow )
    {
        Calendar c = Calendar.getInstance();
        c.add( Calendar.MINUTE, ( minFromNow ) * ( -1 ) );
        return c.getTime();
    }
}
