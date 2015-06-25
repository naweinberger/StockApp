package com.palindromicstudios.stockapp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Natan on 6/25/2015.
 */
public class StockQuote {

    String symbol;
    double price;
    double change;

    public StockQuote(String symbol, double price, double change) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public StockQuote(String symbol, String price, String change) {
        this(symbol, Double.valueOf(price), Double.valueOf(change));
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFormattedPrice() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return numberFormat.format(this.price);
    }

    public Double getChange() {
        return this.change;
    }

    public String getFormattedChange() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(this.change);
    }
}
