package com.assignmentsolver.stocks.simple;

import com.assignmentsolver.stocks.Stock;
import com.assignmentsolver.stocks.Trade;
import com.assignmentsolver.stocks.TradeType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class CommonStock implements Stock {

    protected String symbol;
    protected double lastDividend;
    protected double parValue;
    protected List<Trade> trades = new ArrayList<>();

    public CommonStock(String symbol, double lastDividend, double parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public double calculateDividendYield(double marketPrice) {
        return lastDividend / marketPrice;
    }

    @Override
    public double calculatePERatio(double marketPrice) {
        return marketPrice / lastDividend;
    }

    @Override
    public double calculateVolumeWeightedPrice(Instant from, Instant to) {

        /* This could be optimised further with some information around typical usage profiles. For example, it might
        make sense to use a sorted data structure for trades depending on the frequency/number of trades and the
        frequency/nature of the volume-weighted price calculations (for example, if it's always for the last 15
        minutes only).
         */

        class WeightedPriceData {
            double tradePriceQuantity = 0;
            double quantity = 0;

            WeightedPriceData(double tradePriceQuantity, double quantity) {
                this.tradePriceQuantity = tradePriceQuantity;
                this.quantity = quantity;
            }

            WeightedPriceData accumulate(WeightedPriceData next) {
                return new WeightedPriceData(tradePriceQuantity + next.tradePriceQuantity, quantity + next.quantity);
            }

            double getVolumeWeightedPrice() {
                return tradePriceQuantity / quantity;
            }
        }

        return trades.stream()
                .filter(trade -> trade.getTimestamp().isAfter(from))
                .filter(trade -> trade.getTimestamp().isBefore(to))
                .map(trade -> new WeightedPriceData(trade.getPrice() * trade.getQuantity(), trade.getQuantity()))
                .reduce(WeightedPriceData::accumulate)
                .orElse(new WeightedPriceData(0, 1)).getVolumeWeightedPrice();
    }

    @Override
    public Trade performTrade(TradeType tradeType, Instant timestamp, long quantity, double tradePrice) {
        Trade trade = new SimpleTrade(this, tradeType, timestamp, quantity, tradePrice);
        trades.add(trade);
        return trade;
    }

    @Override
    public List<Trade> getTrades() {
        return Collections.unmodifiableList(trades);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonStock that = (CommonStock) o;

        if (Double.compare(that.lastDividend, lastDividend) != 0) return false;
        if (Double.compare(that.parValue, parValue) != 0) return false;
        return symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = symbol.hashCode();
        temp = Double.doubleToLongBits(lastDividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(parValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CommonStock{" +
                "symbol='" + symbol + '\'' +
                ", lastDividend=" + lastDividend +
                ", parValue=" + parValue +
                '}';
    }
}
