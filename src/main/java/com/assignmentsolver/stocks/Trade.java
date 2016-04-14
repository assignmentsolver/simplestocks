package com.assignmentsolver.stocks;

import java.time.Instant;

/**
 *
 */
public interface Trade {

    Stock getStock();

    TradeType getTradeType();

    Instant getTimestamp();

    long getQuantity();

    double getPrice();

}
