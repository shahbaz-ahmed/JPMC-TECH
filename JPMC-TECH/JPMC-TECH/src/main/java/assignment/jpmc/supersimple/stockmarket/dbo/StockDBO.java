package assignment.jpmc.supersimple.stockmarket.dbo;

import java.math.BigDecimal;
import java.util.List;

public class StockDBO
{

    private String stockSymbol;

    private StockType stockType;

    private BigDecimal lastDividend;

    private BigDecimal fixedDividend;

    private BigDecimal parValue;

    private List<TradeDBO> tradeDBOList;

    public StockDBO( String stockSymbol, StockType stockType, BigDecimal lastDividend, BigDecimal fixedDividend,
                     BigDecimal parValue, List<TradeDBO> tradeDBOList )
    {
        super();
        this.stockSymbol = stockSymbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.tradeDBOList = tradeDBOList;
    }

    public String getStockSymbol()
    {
        return stockSymbol;
    }

    public void setStockSymbol( String stockSymbol )
    {
        this.stockSymbol = stockSymbol;
    }

    public StockType getStockType()
    {
        return stockType;
    }

    public void setStockType( StockType stockType )
    {
        this.stockType = stockType;
    }

    public BigDecimal getLastDividend()
    {
        return lastDividend;
    }

    public void setLastDividend( BigDecimal lastDividend )
    {
        this.lastDividend = lastDividend;
    }

    public BigDecimal getFixedDividend()
    {
        return fixedDividend;
    }

    public void setFixedDividend( BigDecimal fixedDividend )
    {
        this.fixedDividend = fixedDividend;
    }

    public BigDecimal getParValue()
    {
        return parValue;
    }

    public void setParValue( BigDecimal parValue )
    {
        this.parValue = parValue;
    }

    public List<TradeDBO> getTradeDBOList()
    {
        return tradeDBOList;
    }

    public void setTradeDBOList( List<TradeDBO> tradeDBOList )
    {
        this.tradeDBOList = tradeDBOList;
    }

}
