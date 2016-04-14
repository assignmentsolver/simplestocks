package com.assignmentsolver.stocks.simple;

import com.assignmentsolver.stocks.Exchange;
import com.assignmentsolver.stocks.Stock;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
public class GBCExchange implements Exchange {

    // Primary use-case in requirements is look-up by symbol, so this implementation uses a map
    protected Map<String, Stock> stocks;

    public GBCExchange() {
        try {
            loadFromInternalCSVResource();
        } catch (IOException e) {
            throw new Error("Failed to load internal GBCE resource", e);
        }
    }

    @Override
    public Collection<Stock> getStocks() {
        return stocks.values();
    }

    @Override
    public Stock getStockBySymbol(String symbol) {
        return stocks.get(symbol);
    }

    @Override
    public double getAllShareIndex(Instant from, Instant to) {

        // Could have used something like Apache Commons Math for calculating geometric mean.

        class MeanData {
            long count = 0;
            double product = 1;

            MeanData(long count, double product) {
                this.count = count;
                this.product = product;
            }

            MeanData accumulate(MeanData next) {
                return new MeanData(count + next.count, product * next.product);
            }

            double getGeometricMean() {
                return Math.pow(product, 1.0 / count);
            }
        }

        return stocks.values().stream()
                .map(stock -> stock.calculateVolumeWeightedPrice(from, to))
                .filter(price -> price > 0)
                .map(price -> new MeanData(1, price))
                .reduce(MeanData::accumulate)
                .orElse(new MeanData(0, 0)).getGeometricMean();
    }

    private void loadFromInternalCSVResource() throws IOException {

        try (
                InputStream stream = getClass().getResourceAsStream("/gbce.csv");
                InputStreamReader streamReader = new InputStreamReader(stream)) {

            stocks = StreamSupport.stream(CSVFormat.RFC4180.withHeader().parse(streamReader).spliterator(), false)
                    .map(record -> record.get("Type").equals("Common") ?
                            new CommonStock(record.get("Stock Symbol"), Double.parseDouble(record.get("Last Dividend")),
                                    Double.parseDouble(record.get("Par Value"))) :
                            new PreferredStock(record.get("Stock Symbol"), Double.parseDouble(record.get("Last Dividend")),
                                    Double.parseDouble(record.get("Par Value")), Double.parseDouble(record.get("Fixed Dividend"))))
                    .collect(Collectors.toMap(Stock::getSymbol, Function.identity()));

        }

    }

}
