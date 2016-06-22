package assignment.jpmc.supersimple.stockmarket.dao;

import java.util.List;

import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;

public interface TradeDao
{
    List<TradeDBO> findTradesByStockSymbol( String stockSymbol )
        throws StockException;

    List<TradeDBO> findAllTrade();

    Boolean addTrade( TradeDBO dbo )
        throws StockException;

    List<TradeDBO> getTradeRecordsByTime( int minutes );

    List<TradeDBO> getTradeRecordsByStockAndTime( String stockSymbol, int minutes );

}
