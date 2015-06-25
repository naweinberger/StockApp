package com.palindromicstudios.stockapp;

import java.util.ArrayList;

/**
 * Created by Natan on 6/24/2015.
 */
public class StockUpdateEvent {

    private ArrayList<StockQuote> quotes;

    public StockUpdateEvent(ArrayList<StockQuote> quotes) {
        this.quotes = quotes;
    }

    public ArrayList<StockQuote> getQuotes() {
        return this.quotes;
    }

}
