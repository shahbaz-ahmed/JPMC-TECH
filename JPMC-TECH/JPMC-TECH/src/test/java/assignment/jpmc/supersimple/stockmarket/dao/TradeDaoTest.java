package assignment.jpmc.supersimple.stockmarket.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import assignment.jpmc.supersimple.stockmarket.dao.impl.TradeDaoImpl;
import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.StockType;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeType;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public class TradeDaoTest
{

    private TradeDao service;

    @Before
    public void setUp()
    {
        this.service = new TradeDaoImpl( getDefaultStocks() );
    }

    @Test
    public void test_getTradeRecordsByStockAndTime()
        throws StockException
    {
        recordDefaultTrades();
        List<TradeDBO> tradeDBOs = service.getTradeRecordsByStockAndTime( "JOE", 5 );
        Assert.assertNotNull( tradeDBOs );
        Assert.assertEquals( 4, tradeDBOs.size() );
    }

    @Test
    public void test_getTradeRecordsByTime()
        throws StockException
    {
        recordDefaultTrades();
        List<TradeDBO> tradeDBOs = service.getTradeRecordsByTime( 5 );
        Assert.assertNotNull( tradeDBOs );
        Assert.assertEquals( 8, tradeDBOs.size() );
    }

    @Test
    public void test_findAllTrade()
        throws StockException
    {
        recordDefaultTrades();
        List<TradeDBO> tradeDBOs = service.findAllTrade();
        Assert.assertNotNull( tradeDBOs );
        Assert.assertEquals( 22, tradeDBOs.size() );
    }

    @Test
    public void test_addTrade()
        throws StockException
    {

        TradeDBO tradeDBO =
            new TradeDBO( "POP", getTimestamp( 4 ), new BigInteger( "1" ), TradeType.BUY, new BigDecimal( 120 ) );
        Boolean flag = service.addTrade( tradeDBO );
        Assert.assertTrue( flag );

        List<TradeDBO> tradeDBOs = service.findTradesByStockSymbol( "POP" );
        Assert.assertNotNull( tradeDBOs );
        Assert.assertEquals( 1, tradeDBOs.size() );
        Assert.assertEquals( "POP", tradeDBOs.get( 0 ).getStockSymbol() );
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

    private void recordDefaultTrades()
        throws StockException
    {
        service.addTrade( new TradeDBO( "TEA", getTimestamp( 10 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 120 ) ) );

        service.addTrade( new TradeDBO( "TEA", getTimestamp( 10 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 120 ) ) );
        service.addTrade( new TradeDBO( "TEA", getTimestamp( 4 ), new BigInteger( "3" ), TradeType.BUY,
                                        new BigDecimal( 130 ) ) );
        service.addTrade( new TradeDBO( "TEA", getTimestamp( 20 ), new BigInteger( "1" ), TradeType.BUY,
                                        new BigDecimal( 90 ) ) );
        service.addTrade( new TradeDBO( "TEA", getTimestamp( 100 ), new BigInteger( "4" ), TradeType.BUY,
                                        new BigDecimal( 125 ) ) );
        service.addTrade( new TradeDBO( "TEA", getTimestamp( 104 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 150 ) ) );

        service.addTrade( new TradeDBO( "POP", getTimestamp( 2 ), new BigInteger( "1" ), TradeType.BUY,
                                        new BigDecimal( 120 ) ) );
        service.addTrade( new TradeDBO( "POP", getTimestamp( 3 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 125 ) ) );
        service.addTrade( new TradeDBO( "POP", getTimestamp( 10 ), new BigInteger( "5" ), TradeType.BUY,
                                        new BigDecimal( 150 ) ) );

        service.addTrade( new TradeDBO( "ALE", getTimestamp( 14 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 80 ) ) );
        service.addTrade( new TradeDBO( "ALE", getTimestamp( 6 ), new BigInteger( "5" ), TradeType.BUY,
                                        new BigDecimal( 85 ) ) );
        service.addTrade( new TradeDBO( "ALE", getTimestamp( 3 ), new BigInteger( "6" ), TradeType.BUY,
                                        new BigDecimal( 90 ) ) );
        service.addTrade( new TradeDBO( "ALE", getTimestamp( 88 ), new BigInteger( "3" ), TradeType.BUY,
                                        new BigDecimal( 50 ) ) );

        service.addTrade( new TradeDBO( "GIN", getTimestamp( 109 ), new BigInteger( "8" ), TradeType.BUY,
                                        new BigDecimal( 200 ) ) );
        service.addTrade( new TradeDBO( "GIN", getTimestamp( 7 ), new BigInteger( "12" ), TradeType.BUY,
                                        new BigDecimal( 300 ) ) );

        service.addTrade( new TradeDBO( "JOE", getTimestamp( 107 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 400 ) ) );
        service.addTrade( new TradeDBO( "JOE", getTimestamp( 3 ), new BigInteger( "4" ), TradeType.BUY,
                                        new BigDecimal( 500 ) ) );
        service.addTrade( new TradeDBO( "JOE", getTimestamp( 3 ), new BigInteger( "7" ), TradeType.BUY,
                                        new BigDecimal( 300 ) ) );
        service.addTrade( new TradeDBO( "JOE", getTimestamp( 1 ), new BigInteger( "8" ), TradeType.BUY,
                                        new BigDecimal( 260 ) ) );
        service.addTrade( new TradeDBO( "JOE", getTimestamp( 8 ), new BigInteger( "9" ), TradeType.BUY,
                                        new BigDecimal( 200 ) ) );
        service.addTrade( new TradeDBO( "JOE", getTimestamp( 99 ), new BigInteger( "2" ), TradeType.BUY,
                                        new BigDecimal( 400 ) ) );
        service.addTrade( new TradeDBO( "JOE", getTimestamp( 2 ), new BigInteger( "6" ), TradeType.BUY,
                                        new BigDecimal( 350 ) ) );

    }

    private Date getTimestamp( int minFromNow )
    {
        Calendar c = Calendar.getInstance();
        c.add( Calendar.MINUTE, ( minFromNow ) * ( -1 ) );
        return c.getTime();
    }
}
