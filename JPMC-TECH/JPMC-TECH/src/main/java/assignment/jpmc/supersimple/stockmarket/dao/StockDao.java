package assignment.jpmc.supersimple.stockmarket.dao;

import java.util.List;

import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public interface StockDao
{
    StockDBO findStockBySymbol( String stockSymbol )
        throws StockException;

    Boolean addStock( StockDBO stock )
        throws StockException;

    Boolean deleteStock( StockDBO stock )
        throws StockException;

    List<StockDBO> retrieveAllStocks()
        throws StockException;
}
