package com.assignmentsolver.stocks.simple;

import com.assignmentsolver.stocks.Stock;
import com.assignmentsolver.stocks.Trade;
import com.assignmentsolver.stocks.TradeType;

import java.time.Instant;

/**
 *
 */
public class SimpleTrade implements Trade {

    private Stock stock;
    private TradeType tradeType;
    private Instant timestamp;
    private long quantity;
    private double price;

    public SimpleTrade(Stock stock, TradeType tradeType, Instant timestamp, long quantity, double price) {
        this.stock = stock;
        this.tradeType = tradeType;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public Stock getStock() {
        return stock;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public TradeType getTradeType() {
        return tradeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTrade that = (SimpleTrade) o;

        if (quantity != that.quantity) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (!stock.equals(that.stock)) return false;
        if (tradeType != that.tradeType) return false;
        return timestamp.equals(that.timestamp);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = stock.hashCode();
        result = 31 * result + tradeType.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
