package com.palindromicstudios.stockapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        label = (TextView) view.findViewById(R.id.label);
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
        label.setText(event.toString());
    }
}

