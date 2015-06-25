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

    public StockPriceFetcher() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22YHOO%22%2C%22AAPL%22%2C%22GOOG%22%2C%22MSFT%22)%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json";

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

            String symbol = "";
            String price = "";

            ArrayList<StockQuote> quotes = new ArrayList<StockQuote>();

            for (int i = 0; i < items.length(); i++) {
                symbol = items.getJSONObject(i).getString("Symbol");
                price = items.getJSONObject(i).getString("LastTradePriceOnly");
                quotes.add(new StockQuote(symbol, price));
            }

            MyBus.getInstance().post(new StockUpdateEvent(quotes));
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

}
