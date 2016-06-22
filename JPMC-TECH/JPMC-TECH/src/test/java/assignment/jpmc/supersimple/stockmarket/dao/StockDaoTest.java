package assignment.jpmc.supersimple.stockmarket.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import assignment.jpmc.supersimple.stockmarket.dao.impl.StockDaoImpl;
import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.StockType;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public class StockDaoTest
{

    private StockDao service;

    @Before
    public void setUp()
    {
        this.service = new StockDaoImpl( getDefaultStocks() );
    }

    @Test
    public void test_findStockBySymbol()
        throws StockException
    {
        StockDBO stockDbo = service.findStockBySymbol( "POP" );
        Assert.assertNotNull( stockDbo );
        Assert.assertEquals( StockType.COMMON, stockDbo.getStockType() );
        BigDecimal toTest = new BigDecimal( 100 );
        toTest = toTest.setScale( 0, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, stockDbo.getParValue() );
    }

    @Test
    public void test_addStock()
        throws StockException
    {
        StockDBO stoclDbo =
            new StockDBO( "XYZ", StockType.COMMON, new BigDecimal( 231 ), null, new BigDecimal( 600 ), null );
        Boolean flag = service.addStock( stoclDbo );

        StockDBO stockDbo = service.findStockBySymbol( "XYZ" );

        Assert.assertTrue( flag );
        Assert.assertEquals( StockType.COMMON, stockDbo.getStockType() );
        BigDecimal toTest = new BigDecimal( 231 );
        toTest = toTest.setScale( 0, RoundingMode.HALF_EVEN );
        Assert.assertEquals( toTest, stockDbo.getLastDividend() );
    }

    @Test
    public void test_retrieveAllStocks()
        throws StockException
    {
        List<StockDBO> stockDbos = service.retrieveAllStocks();
        Assert.assertNotNull( stockDbos );
        Assert.assertEquals( 5, stockDbos.size() );
    }

    @Test( expected = StockException.class )
    public void test_deleteStock()
        throws StockException
    {
        StockDBO stockDbo = service.findStockBySymbol( "POP" );
        Boolean flag = service.deleteStock( stockDbo );
        Assert.assertTrue( flag );
        service.findStockBySymbol( "POP" );
        List<StockDBO> stockDbos = service.retrieveAllStocks();
        Assert.assertNotNull( stockDbos );
        Assert.assertEquals( 4, stockDbos.size() );
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
}
