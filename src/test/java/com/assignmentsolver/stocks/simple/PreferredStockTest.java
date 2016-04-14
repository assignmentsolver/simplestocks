package com.assignmentsolver.stocks.simple;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class PreferredStockTest {

    private PreferredStock stock;

    @Before
    public void setUp() throws Exception {
        stock = new PreferredStock("PTEST", 8, 100, .02);
    }

    @Test
    public void calculateDividendYield() throws Exception {
        assertEquals(.025, stock.calculateDividendYield(80), .001);
    }

}