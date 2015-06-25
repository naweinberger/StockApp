package com.palindromicstudios.stockapp;

/**
 * Created by Natan on 6/24/2015.
 */
public class StockUpdateEvent {

    private String symbol;
    private double price;

    public StockUpdateEvent(String symbol, String price) {
        this(symbol, Double.valueOf(price));
    }

    public StockUpdateEvent(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return new StringBuilder(this.symbol)
                .append(": ")
                .append(String.valueOf(this.price))
                .toString();
    }
}
