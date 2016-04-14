package com.assignmentsolver.stocks;

import java.time.Instant;
import java.util.Collection;

/**
 *
 */
public interface Exchange {

    Collection<Stock> getStocks();

    Stock getStockBySymbol(String symbol);

    double getAllShareIndex(Instant from, Instant to);
}
