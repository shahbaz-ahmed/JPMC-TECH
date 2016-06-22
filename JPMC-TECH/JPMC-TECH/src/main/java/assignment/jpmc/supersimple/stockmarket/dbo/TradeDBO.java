package assignment.jpmc.supersimple.stockmarket.dbo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TradeDBO
{

    private String stockSymbol;

    private Date timestamp;

    private BigInteger quantity;

    private TradeType tradeType;

    private BigDecimal price;

    public TradeDBO( String stockSymbol, Date timestamp, BigInteger quantity, TradeType tradeType, BigDecimal price )
    {
        this.stockSymbol = stockSymbol;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.tradeType = tradeType;
        this.price = price;
    }

    public String getStockSymbol()
    {
        return stockSymbol;
    }

    public void setStockSymbol( String stockSymbol )
    {
        this.stockSymbol = stockSymbol;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( Date timestamp )
    {
        this.timestamp = timestamp;
    }

    public BigInteger getQuantity()
    {
        return quantity;
    }

    public void setQuantity( BigInteger quantity )
    {
        this.quantity = quantity;
    }

    public TradeType getTradeType()
    {
        return tradeType;
    }

    public void setTradeType( TradeType tradeType )
    {
        this.tradeType = tradeType;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

}
