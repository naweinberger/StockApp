package com.palindromicstudios.stockapp;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    TextView label;
    Button refresh;
    private static StockPriceFetcher stockPriceFetcher;
    private static ArrayList<StockQuote> quotes;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.ticker_recyclerview);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        refresh = (Button) view.findViewById(R.id.generate_event);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stockPriceFetcher != null) {
                    stockPriceFetcher.cancel(true);
                    stockPriceFetcher = null;
                }
                stockPriceFetcher = new StockPriceFetcher();
                stockPriceFetcher.execute();
            }
        });

        updateQuotes();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        MyBus.getInstance().unregister(this);
        super.onPause();
    }

    @Subscribe public void onStockUpdateEvent(StockUpdateEvent event) {

        if (quotes == null) {
            quotes = new ArrayList<StockQuote>();
        }

        quotes.clear();
        quotes.addAll(event.getQuotes());

        if (mAdapter == null) {
            mAdapter = new TickerAdapter(quotes);
            mRecyclerView.setAdapter(mAdapter);
        }

        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        //mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe public void onStockRefreshingEvent(StockRefreshingEvent event) {
        if (event.getCompletionStatus() == true) {
            refresh.setText("Update quotes");
            refresh.setClickable(true);
        }
        else {
            refresh.setText("Refreshing...");
            refresh.setClickable(false);
        }

    }

    public void updateQuotes() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            StockPriceFetcher task = new StockPriceFetcher();
                            task.execute();
                        } catch (Exception e) {
                            Log.e("StockApp", e.toString());
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 30000); // in milliseconds
    }
}

