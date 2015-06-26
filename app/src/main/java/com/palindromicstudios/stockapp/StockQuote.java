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
    double change, percentChange;
    double open, yearLow, yearHigh, dayLow, dayHigh, dividend, eps, peRatio;

    public StockQuote() {}

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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFormattedPrice() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return numberFormat.format(this.price);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getChange() {
        return this.change;
    }

    public String getFormattedChange() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(this.change);
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    public double getOpen() {
        return open;
    }

    public String getFormattedOpen() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return numberFormat.format(this.open);
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getYearLow() {
        return yearLow;
    }

    public String getFormattedYearLow() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(this.yearLow);
    }

    public void setYearLow(double yearLow) {
        this.yearLow = yearLow;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    public String getFormattedYearHigh() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(this.yearHigh);
    }

    public void setYearHigh(double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public String getFormattedDayLow() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(this.dayLow);
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public String getFormattedDayHigh() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(this.dayHigh);
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDividend() {
        return dividend;
    }

    public void setDividend(double dividend) {
        this.dividend = dividend;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(double peRatio) {
        this.peRatio = peRatio;
    }
}
