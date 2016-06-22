package assignment.jpmc.supersimple.stockmarket.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.jpmc.supersimple.stockmarket.dao.StockDao;
import assignment.jpmc.supersimple.stockmarket.dao.TradeDao;
import assignment.jpmc.supersimple.stockmarket.dao.impl.StockDaoImpl;
import assignment.jpmc.supersimple.stockmarket.dao.impl.TradeDaoImpl;
import assignment.jpmc.supersimple.stockmarket.dbo.StockDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeDBO;
import assignment.jpmc.supersimple.stockmarket.dbo.TradeType;
import assignment.jpmc.supersimple.stockmarket.exception.InvalidPriceException;
import assignment.jpmc.supersimple.stockmarket.exception.StockException;
import assignment.jpmc.supersimple.stockmarket.ratios.impl.DividendYield;
import assignment.jpmc.supersimple.stockmarket.ratios.impl.FinancialRatioFactory;
import assignment.jpmc.supersimple.stockmarket.ratios.impl.PriceEarningsRatio;
import assignment.jpmc.supersimple.stockmarket.ratios.impl.VolumeWeightedStockPrice;
import assignment.jpmc.supersimple.stockmarket.service.FinancialCalculationsService;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioContext;
import assignment.jpmc.supersimple.stockmarket.util.FinancialRatioType;
import assignment.jpmc.supersimple.stockmarket.util.GBCEUtils;

public class FinancialCalculationsServiceImpl
    implements FinancialCalculationsService
{

    private TradeDao tradeDao;

    private StockDao stockDao;

    private DividendYield dividendYield;

    private PriceEarningsRatio priceEarningsRatio;

    private VolumeWeightedStockPrice volumeWeightedStockPrice;

    private Map<String, StockDBO> stocks;

    private static final String PRECISION = "0.001";

    private static final Logger LOGGER = Logger.getLogger( FinancialCalculationsServiceImpl.class.getName() );

    public FinancialCalculationsServiceImpl( Map<String, StockDBO> stocks )
    {
        super();
        // injections
        this.dividendYield = new DividendYield();
        this.priceEarningsRatio = new PriceEarningsRatio();
        this.volumeWeightedStockPrice = new VolumeWeightedStockPrice();
        this.stocks = stocks;
        this.tradeDao = new TradeDaoImpl( stocks );
        this.stockDao = new StockDaoImpl( stocks );
    }

    /**
     * This method calculates Dividend Yield Ratio for a stock with given price .
     * 
     * @param String stockSymbol, BigDecimal price
     * @return BigDecimal as value of the ratio
     */
    public BigDecimal calculateDividendYield( String stockSymbol, BigDecimal price )
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( price );
        ArrayList<StockDBO> stock = new ArrayList<StockDBO>();
        stock.add( stockDao.findStockBySymbol( stockSymbol ) );
        context.setStocks( stock );
        BigDecimal value = FinancialRatioFactory.getCalculator( FinancialRatioType.DIVIDEND_YIELD ).calculate( context );
        value = value.setScale( FinancialRatioContext.ROUNDING_SCALE, BigDecimal.ROUND_HALF_EVEN );
        LOGGER.log( Level.FINE, "calculateDividendYield for " + stockSymbol + " is " + value );
        return value;
    }

    /**
     * This method calculates P/E Ratio for a stock with given price .
     * 
     * @param String stockSymbol, BigDecimal price
     * @return BigDecimal as value of the ratio
     */
    public BigDecimal calculatePERatio( String stockSymbol, BigDecimal price )
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();
        context.setPrice( price );
        ArrayList<StockDBO> stock = new ArrayList<StockDBO>();
        stock.add( stockDao.findStockBySymbol( stockSymbol ) );
        context.setStocks( stock );
        BigDecimal value =
            FinancialRatioFactory.getCalculator( FinancialRatioType.PRICE_EARNING_RATION ).calculate( context );
        value = value.setScale( FinancialRatioContext.ROUNDING_SCALE, BigDecimal.ROUND_HALF_EVEN );
        LOGGER.log( Level.FINE, "calculatePERatio for " + stockSymbol + " is " + value );
        return value;
    }

    public Boolean recordTrade( String symbol, Date timestamp, BigInteger quantity, TradeType tradeType,
                                BigDecimal price )
        throws StockException
    {
        StockDBO stock = stockDao.findStockBySymbol( symbol );
        TradeDBO tradeDBO = new TradeDBO( symbol, timestamp, quantity, tradeType, price );
        stock.getTradeDBOList().add( tradeDBO );
        stockDao.addStock( stock );
        LOGGER.log( Level.FINE, "stock " + symbol + " recored" );
        return Boolean.TRUE;
    }

    /**
     * This method calculates Volume Weighted Stock Price Ratio for past minutes.
     * 
     * @param int minutes
     * @return BigDecimal as value of the ratio
     */
    public BigDecimal calculateVolumeWeightedStockPrice( int minutes )
        throws StockException, InvalidPriceException
    {
        FinancialRatioContext context = new FinancialRatioContext();

        List<TradeDBO> tradeDBOs = tradeDao.getTradeRecordsByTime( minutes );
        context.setTrades( tradeDBOs );
        BigDecimal value =
            FinancialRatioFactory.getCalculator( FinancialRatioType.VOLUME_WEIGHTED_STOCK_PRICE ).calculate( context );
        value = value.setScale( FinancialRatioContext.ROUNDING_SCALE, BigDecimal.ROUND_HALF_EVEN );

        LOGGER.log( Level.FINE, "calculateVolumeWeightedStockPrice for " + tradeDBOs.size() + " trade/s is " + value );
        return value;
    }

    /**
     * This method calculates All Share Index Using GM.
     * @return BigDecimal as value of the index
     */
    public BigDecimal calculateGBCEAllShareIndexUsingGM()
        throws StockException, InvalidPriceException
    {
        BigDecimal factor = BigDecimal.ONE;
        List<TradeDBO> tradeDBOs = new ArrayList<TradeDBO>();
        for ( String stockSymbol : getStocks().keySet() )
        {
            FinancialRatioContext context = new FinancialRatioContext();
            tradeDBOs = tradeDao.getTradeRecordsByStockAndTime( stockSymbol, -1 );
            context.setTrades( tradeDBOs );
            BigDecimal p = volumeWeightedStockPrice.calculate( context );
            if ( p != null && BigDecimal.ZERO.compareTo( p ) < 0 )
            {
                factor = factor.multiply( p, MathContext.DECIMAL128 );
            }
        }

        BigDecimal value =
            GBCEUtils.calculateNthRoot( getStocks().size(), factor, new BigDecimal( PRECISION ) ).setScale( FinancialRatioContext.ROUNDING_SCALE,
                                                                                                            BigDecimal.ROUND_HALF_EVEN );
        LOGGER.log( Level.FINE, "calculateGBCEAllShareIndexUsingGM for " + tradeDBOs.size() + " trade/s is " + value );
        return value;

    }

    public DividendYield getDividendYield()
    {
        return dividendYield;
    }

    public void setDividendYield( DividendYield dividendYield )
    {
        this.dividendYield = dividendYield;
    }

    public PriceEarningsRatio getPriceEarningsRatio()
    {
        return priceEarningsRatio;
    }

    public void setPriceEarningsRatio( PriceEarningsRatio priceEarningsRatio )
    {
        this.priceEarningsRatio = priceEarningsRatio;
    }

    public VolumeWeightedStockPrice getVolumeWeightedStockPrice()
    {
        return volumeWeightedStockPrice;
    }

    public void setVolumeWeightedStockPrice( VolumeWeightedStockPrice volumeWeightedStockPrice )
    {
        this.volumeWeightedStockPrice = volumeWeightedStockPrice;
    }

    public Map<String, StockDBO> getStocks()
    {
        return stocks;
    }

    public void setStocks( Map<String, StockDBO> stocks )
    {
        this.stocks = stocks;
    }

    public TradeDao getTradeDao()
    {
        return tradeDao;
    }

    public void setTradeDao( TradeDao tradeDao )
    {
        this.tradeDao = tradeDao;
    }

    public StockDao getStockDao()
    {
        return stockDao;
    }

    public void setStockDao( StockDao stockDao )
    {
        this.stockDao = stockDao;
    }

}
