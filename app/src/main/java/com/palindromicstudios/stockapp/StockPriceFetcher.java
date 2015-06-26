package com.palindromicstudios.stockapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Natan on 6/24/2015.
 */
public class StockPriceFetcher extends AsyncTask<Void, Void, String> {

    final private String SYMBOL = "Symbol",
            PRICE = "LastTradePriceOnly",
            CHANGE = "Change",
            PERCENT_CHANGE = "PercentChange",
            OPEN = "Open",
            DAY_LOW = "DaysLow",
            DAY_HIGH = "DaysHigh",
            YEAR_LOW = "YearLow",
            YEAR_HIGH = "YearHigh",
            EPS = "EarningsShare",
            PE_RATIO = "PERatio",
            DIVIDEND = "DividendShare";

    public StockPriceFetcher() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MyBus.getInstance().post(new StockRefreshingEvent(false));
    }

    @Override
    protected String doInBackground(Void... params) {

        String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22YHOO%22%2C%22AAPL%22%2C%22GOOG%22%2C%22MSFT%22%2C%22SBUX%22%2C%22QQQ%22%2C%22SPY%22%2C%22AAPL%22%2C%22GILD%22%2C%22TSLA%22%2C%22NFLX%22%2C%22GE%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json";

        // Query Yahoo! for stock price data
        // Data will be cleaned later in onPostExecute
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.e("Error", e.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Clean data
        try {
            JSONObject root = new JSONObject(result);

            JSONArray items = root.getJSONObject("query").getJSONObject("results").getJSONArray("quote");
            JSONObject item;


            ArrayList<StockQuote> quotes = new ArrayList<StockQuote>();

            for (int i = 0; i < items.length(); i++) {
                item = items.getJSONObject(i);

                StockQuote quote = new StockQuote();

                quote.setSymbol(item.getString(SYMBOL));
                quote.setPrice(item.getDouble(PRICE));
                quote.setChange(item.getDouble(CHANGE));
                quote.setOpen(item.getDouble(OPEN));
                quote.setDayLow(item.getDouble(DAY_LOW));
                quote.setDayHigh(item.getDouble(DAY_HIGH));
                quote.setYearLow(item.getDouble(YEAR_LOW));
                quote.setYearHigh(item.getDouble(YEAR_HIGH));
                //quote.setEps(item.getDouble(EPS));
                //quote.setPeRatio(item.getDouble(PE_RATIO));

                // Dividend may be null so perform a check
                String dividendValue = item.getString(DIVIDEND);
                if (dividendValue.isEmpty()) {
                    dividendValue = "0.00";
                }
                //quote.setDividend(item.getDouble(dividendValue));

                // Percent change is returned with a percent sign on the end which interferes with the double parsing
                String percentChange = item.getString(PERCENT_CHANGE);
                quote.setPercentChange(Double.valueOf(percentChange.substring(0, percentChange.length()-1)));

                quotes.add(quote);
            }

            MyBus.getInstance().post(new StockRefreshingEvent(true));
            MyBus.getInstance().post(new StockUpdateEvent(quotes));
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

}
