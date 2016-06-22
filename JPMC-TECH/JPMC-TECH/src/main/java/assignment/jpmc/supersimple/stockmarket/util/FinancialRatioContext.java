package assignment.jpmc.supersimple.stockmarket.util;

import java.math.BigDecimal;
import java.util.List;

import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public class FinancialRatioContext
{

    public static final int ROUNDING_SCALE = 2;

    public static final int ZERO = 0;

    private BigDecimal price;

    private int munites;

    private List<StockDBO> stocks;

    private List<TradeDBO> trades;

    public BigDecimal getPrice()
        throws InvalidPriceException
    {
        if ( price == null || price.signum() == -1 )
        {
            throw new InvalidPriceException( "Invalid price " + price );
        }
        return price;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

    public int getMunites()
    {
        return munites;
    }

    public void setMunites( int munites )
    {
        this.munites = munites;
    }

    public List<StockDBO> getStocks()
    {
        return stocks;
    }

    public void setStocks( List<StockDBO> stocks )
    {
        this.stocks = stocks;
    }

    public StockDBO getFirstStock()
        throws StockException
    {
        if ( stocks == null || stocks.isEmpty() )
        {
            throw new StockException( "Stocks does not exist" );
        }
        return stocks.get( ZERO );
    }

    public List<TradeDBO> getTrades()
    {
        return trades;
    }

    public void setTrades( List<TradeDBO> trades )
    {
        this.trades = trades;
    }
}
