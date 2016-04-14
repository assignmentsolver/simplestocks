package com.assignmentsolver.stocks.simple;

import com.assignmentsolver.stocks.Trade;
import com.assignmentsolver.stocks.TradeType;
import org.junit.Before;
import org.junit.Test;

import java.time.*;

import static org.junit.Assert.*;

/**
 *
 */
public class CommonStockTest {

    private CommonStock stock;
    ZoneId zone = ZoneId.systemDefault();

    @Before
    public void setUp() throws Exception {
        stock = new CommonStock("TEST", 8, 100);
    }

    @Test
    public void calculateDividendYield() throws Exception {
        assertEquals(.1, stock.calculateDividendYield(80), .001);
        assertEquals(.16, stock.calculateDividendYield(50), .001);
    }

    @Test
    public void calculatePERatio() throws Exception {
        assertEquals(10, stock.calculatePERatio(80), .001);
        assertEquals(6.25, stock.calculatePERatio(50), .001);
    }

    @Test
    public void performTrade() throws Exception {
        Instant ts = LocalDateTime.of(2016, Month.APRIL, 13, 9, 45).atZone(zone).toInstant();
        Trade trade = stock.performTrade(TradeType.BUY, ts, 5, 10);
        assertEquals(stock, trade.getStock());
        assertEquals(TradeType.BUY, trade.getTradeType());
        assertEquals(ts, trade.getTimestamp());
        assertEquals(5, trade.getQuantity());
        assertEquals(10, trade.getPrice(), .001);
    }

    @Test
    public void getTrades() throws Exception {
        assertEquals(0, stock.getTrades().size());

        Instant ts = LocalDateTime.of(2016, Month.APRIL, 13, 9, 45).atZone(zone).toInstant();
        Trade trade = stock.performTrade(TradeType.BUY, ts, 5, 10);

        assertEquals(1, stock.getTrades().size());
        assertEquals(trade, stock.getTrades().get(0));
    }

    @Test
    public void calculateVolumeWeightedPrice() throws Exception {
        Instant from = LocalDateTime.of(2016, Month.APRIL, 13, 9, 44).atZone(zone).toInstant();
        Instant to = LocalDateTime.of(2016, Month.APRIL, 13, 9, 59).atZone(zone).toInstant();
        Instant ts1 = LocalDateTime.of(2016, Month.APRIL, 13, 9, 45).atZone(zone).toInstant();
        Instant ts2  = LocalDateTime.of(2016, Month.APRIL, 13, 9, 46).atZone(zone).toInstant();

        assertEquals(0, stock.calculateVolumeWeightedPrice(from, to), .001);

        stock.performTrade(TradeType.BUY, ts1, 5, 3);
        stock.performTrade(TradeType.SELL, ts2, 10, 4);

        assertEquals(3.6666666, stock.calculateVolumeWeightedPrice(from, to), .001);
    }

}