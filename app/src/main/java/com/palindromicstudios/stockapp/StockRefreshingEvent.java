package com.palindromicstudios.stockapp;

/**
 * Created by Natan on 6/25/2015.
 */
public class StockRefreshingEvent {

    private boolean completed;

    public StockRefreshingEvent(boolean completed) {
        this.completed = completed;
    }

    public boolean getCompletionStatus() {
        return this.completed;
    }
}
