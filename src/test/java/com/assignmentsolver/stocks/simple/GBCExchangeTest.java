package com.assignmentsolver.stocks.simple;


import com.assignmentsolver.stocks.TradeType;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;

/**
 *
 */
public class GBCExchangeTest {
    private GBCExchange gbce;
    ZoneId zone = ZoneId.systemDefault();

    @Before
    public void setUp() throws Exception {
        gbce = new GBCExchange();
    }

    @Test
    public void getStocks() throws Exception {
        assertEquals(5, gbce.getStocks().size());
        assertThat(gbce.getStocks(), hasItem(new CommonStock("TEA", 0, 100)));
        assertThat(gbce.getStocks(), hasItem(new CommonStock("POP", 8, 100)));
        assertThat(gbce.getStocks(), hasItem(new CommonStock("ALE", 23, 60)));
        assertThat(gbce.getStocks(), hasItem(new PreferredStock("GIN", 8, 100, .02)));
        assertThat(gbce.getStocks(), hasItem(new CommonStock("JOE", 13, 250)));
    }

    @Test
    public void getStockBySymbol() throws Exception {
        assertEquals(new CommonStock("TEA", 0, 100), gbce.getStockBySymbol("TEA"));
        assertEquals(new PreferredStock("GIN", 8, 100, .02), gbce.getStockBySymbol("GIN"));
    }

    @Test
    public void getAllShareIndex() throws Exception {
        Instant from = LocalDateTime.of(2016, Month.APRIL, 13, 9, 44).atZone(zone).toInstant();
        Instant to = LocalDateTime.of(2016, Month.APRIL, 13, 9, 59).atZone(zone).toInstant();
        Instant ts1 = LocalDateTime.of(2016, Month.APRIL, 13, 9, 45).atZone(zone).toInstant();
        Instant ts2  = LocalDateTime.of(2016, Month.APRIL, 13, 9, 46).atZone(zone).toInstant();

        assertEquals(0.0, gbce.getAllShareIndex(from, to), .001);
        gbce.getStockBySymbol("POP").performTrade(TradeType.BUY, ts1, 5, 40);
        assertEquals(40, gbce.getAllShareIndex(from, to), .001);
        gbce.getStockBySymbol("ALE").performTrade(TradeType.SELL, ts1, 8, 70);
        assertEquals(52.915026, gbce.getAllShareIndex(from, to), .001);
    }

}