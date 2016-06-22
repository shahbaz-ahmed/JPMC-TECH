package assignment.jpmc.supersimple.stockmarket.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.jpmc.supersimple.stockmarket.dao.TradeDao;
import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public class TradeDaoImpl
    implements TradeDao
{
    private Map<String, StockDBO> stocks;

    private static final long MILI = 1000;

    private static final long SECOND = 60;

    private static final Logger LOGGER = Logger.getLogger( TradeDaoImpl.class.getName() );

    public TradeDaoImpl( Map<String, StockDBO> stocks )
    {
        super();
        this.stocks = stocks;
    }

    /**
     * The method finds all trades by stock symbol from database (Map in our case)
     * @param String stockSymbol
     * @return List<StockDBO>
     * @exception StockException
     */
    public List<TradeDBO> findTradesByStockSymbol( String stockSymbol )
        throws StockException
    {
        StockDBO stock = stocks.get( stockSymbol );

        if ( stock == null )
        {
            LOGGER.log( Level.SEVERE, stockSymbol + " does not exist" );
            throw new StockException( stockSymbol + " does not exist" );
        }
        LOGGER.log( Level.FINE, "Found record for stock symbol" + stockSymbol );
        return stock.getTradeDBOList();
    }

    /**
     * The method finds all trades from database (Map in our case)
     * @param String stockSymbol
     * @return List<StockDBO>
     * @exception StockException
     */
    public List<TradeDBO> findAllTrade()
    {
        List<TradeDBO> trades = new ArrayList<TradeDBO>();
        List<StockDBO> list = new ArrayList<StockDBO>();
        list.addAll( stocks.values() );
        for ( StockDBO stockDBO : list )
        {
            if ( stockDBO.getTradeDBOList() != null )
            {
                trades.addAll( stockDBO.getTradeDBOList() );
            }
        }
        return trades;
    }

    /**
     * The method adds trade to database (Map in our case)
     * @param TradeDBO
     * @return Boolean
     * @exception StockException
     */
    public Boolean addTrade( TradeDBO dbo )
        throws StockException
    {
        StockDBO stock = stocks.get( dbo.getStockSymbol() );
        if ( stock.getTradeDBOList() == null )
        {
            stock.setTradeDBOList( new ArrayList<TradeDBO>() );
        }
        LOGGER.log( Level.FINE, dbo.getStockSymbol() + " added." );
        return stock.getTradeDBOList().add( dbo );
    }
    
    /**
     * The method retrieves trades by past time in minutes from database (Map in our case)
     * @param int minutes
     * @return List<TradeDBO>
     * 
     */
    public List<TradeDBO> getTradeRecordsByTime( int minutes )
    {
        List<TradeDBO> tradeDBOs = new ArrayList<TradeDBO>();
        for ( StockDBO stock : stocks.values() )
        {
            tradeDBOs.addAll( getTradeRecordsByStockAndTime( stock.getStockSymbol(), minutes ) );
        }
        LOGGER.log( Level.FINE, tradeDBOs.size() + " record/s added." );
        return tradeDBOs;
    }

    /**
     * The method retrieves trades by stock symbol and
     * past time in minutes from database (Map in our case)
     * @param int minutes
     * @return List<TradeDBO>
     * 
     */
    public List<TradeDBO> getTradeRecordsByStockAndTime( String stockSymbol, int minutes )
    {
        List<TradeDBO> tradeDBOs = new ArrayList<TradeDBO>();
        if ( minutes == -1 )
        {
            tradeDBOs = stocks.get( stockSymbol ).getTradeDBOList();
        }
        else
        {
            Date currentTime = new Date();
            for ( TradeDBO tradeDBO : stocks.get( stockSymbol ).getTradeDBOList() )
            {
                if ( currentTime.getTime() - tradeDBO.getTimestamp().getTime() <= minutes * SECOND * MILI )
                {
                    tradeDBOs.add( tradeDBO );
                }
            }
        }
        LOGGER.log( Level.FINE, tradeDBOs.size() + " record/s for the stock symbol " + stockSymbol + " added." );
        return tradeDBOs;

    }
}
