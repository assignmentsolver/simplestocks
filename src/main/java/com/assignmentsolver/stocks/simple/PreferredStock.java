package com.assignmentsolver.stocks.simple;

/**
 *
 */
public class PreferredStock extends CommonStock {

    private double fixedDividend;

    public PreferredStock(String symbol, double lastDividend, double parValue, double fixedDividend) {
        super(symbol, lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    @Override
    public double calculateDividendYield(double marketPrice) {
        return (fixedDividend * parValue) / marketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PreferredStock that = (PreferredStock) o;

        return Double.compare(that.fixedDividend, fixedDividend) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(fixedDividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PreferredStock{" +
                "symbol='" + symbol + '\'' +
                ", lastDividend=" + lastDividend +
                ", parValue=" + parValue +
                "fixedDividend=" + fixedDividend +
                '}';
    }
}
