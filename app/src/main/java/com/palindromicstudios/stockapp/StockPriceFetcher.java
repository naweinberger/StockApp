package com.palindromicstudios.stockapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
            String symbol = root.getJSONObject("query").getJSONObject("results").getJSONArray("quote").getJSONObject(0).getString("Symbol");
            String price = root.getJSONObject("query").getJSONObject("results").getJSONArray("quote").getJSONObject(0).getString("LastTradePriceOnly");
            Log.i("Price", price);
            MyBus.getInstance().post(new StockUpdateEvent(symbol, price));
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

}
