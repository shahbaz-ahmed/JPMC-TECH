package assignment.jpmc.supersimple.stockmarket.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.jpmc.supersimple.stockmarket.dao.StockDao;
import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public class StockDaoImpl
    implements StockDao
{
    private Map<String, StockDBO> stocks;

    private static final Logger LOGGER = Logger.getLogger( StockDaoImpl.class.getName() );

    public StockDaoImpl( Map<String, StockDBO> stocks )
    {
        super();
        this.stocks = stocks;
    }

    
    /**
     * Given stock symbol, this method returns StockDBO object 
     * @param String stockSymbol
     * @return StockDBO
     * @exception StockException
     */
    public StockDBO findStockBySymbol( String stockSymbol )
        throws StockException
    {
        StockDBO stock = stocks.get( stockSymbol );
        if ( stock == null )
        {
            LOGGER.log( Level.SEVERE, stockSymbol + " does not exist" );
            throw new StockException( stockSymbol + " does not exist" );
        }
        LOGGER.log( Level.FINE, "Found record for stock symbol" + stockSymbol );
        return stock;
    }

    /**
     * The method add stockDBO into database (Map in our case)
     * @param StockDBO
     * @return Boolean
     * @exception StockException
     */
    public Boolean addStock( StockDBO stock )
        throws StockException
    {
        stocks.put( stock.getStockSymbol(), stock );
        LOGGER.log( Level.FINE, stock.getStockSymbol() + " added" );
        return Boolean.TRUE;
    }

    /**
     * The method deletes stockDBO from database (Map in our case)
     * @param StockDBO
     * @return Boolean
     * @exception StockException
     */
    public Boolean deleteStock( StockDBO stock )
        throws StockException
    {
        return ( stocks.remove( stock.getStockSymbol() ) != null );
    }

    /**
     * The method retrieves all stocks from database (Map in our case)
     * @param void
     * @return List<StockDBO>
     * @exception StockException
     */
    public List<StockDBO> retrieveAllStocks()
        throws StockException
    {
        List<StockDBO> list = new ArrayList<StockDBO>();
        list.addAll( stocks.values() );
        LOGGER.log( Level.FINE, list.size() + " record/s retrieved." );
        return list;
    }

}
