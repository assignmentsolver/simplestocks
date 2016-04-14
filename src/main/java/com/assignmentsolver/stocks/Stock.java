package com.assignmentsolver.stocks;

import java.time.Instant;
import java.util.List;

/**
 *
 */
public interface Stock {

    String getSymbol();

    double calculateDividendYield(double marketPrice);

    double calculatePERatio(double marketPrice);

    double calculateVolumeWeightedPrice(Instant from, Instant to);

    Trade performTrade(TradeType tradeType, Instant timestamp, long quantity, double tradePrice);

    List<Trade> getTrades();
}
