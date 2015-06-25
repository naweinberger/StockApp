package com.palindromicstudios.stockapp;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    TextView label;
    private static StockPriceFetcher stockPriceFetcher;
    private static StockQuote[] quotes;

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

        if (quotes == null) {
            quotes = new StockQuote[]{};
        }

        mAdapter = new TickerAdapter(quotes);
        mRecyclerView.setAdapter(mAdapter);

        Button generateButton = (Button) view.findViewById(R.id.generate_event);
        generateButton.setOnClickListener(new View.OnClickListener() {
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
        quotes = event.getQuotes().toArray(quotes);

        if (mAdapter != null) {
            mAdapter = null;
        }

        mAdapter = new TickerAdapter(quotes);
        mRecyclerView.setAdapter(mAdapter);
    }
}

