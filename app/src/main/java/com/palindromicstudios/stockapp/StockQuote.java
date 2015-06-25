package com.palindromicstudios.stockapp;

/**
 * Created by Natan on 6/25/2015.
 */
public class StockQuote {

    String symbol;
    double price;

    public StockQuote(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public StockQuote(String symbol, String price) {
        this(symbol, Double.valueOf(price));
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}
