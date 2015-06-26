package com.palindromicstudios.stockapp;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Natan on 6/25/2015.
 */
public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {
    private ArrayList<StockQuote> mDataset;
    private boolean[] dropdownIndicator;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView symbol, price, change, dayRange, yearRange;
        public CardView container;
        public RelativeLayout expandedInfo;

        public ViewHolder(View v) {
            super(v);
            symbol = (TextView) v.findViewById(R.id.symbol);
            price = (TextView) v.findViewById(R.id.price);
            change = (TextView) v.findViewById(R.id.change);
            dayRange = (TextView) v.findViewById(R.id.day_range);
            yearRange = (TextView) v.findViewById(R.id.year_range);
            container = (CardView) v.findViewById(R.id.card_view);
            expandedInfo = (RelativeLayout) v.findViewById(R.id.expanded_info_container);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TickerAdapter(ArrayList<StockQuote> myDataset) {
        mDataset = myDataset;
        dropdownIndicator = new boolean[mDataset.size()];
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        StockQuote dItem = mDataset.get(position);

        int color;
        double change = mDataset.get(position).getChange();
        if (change >= 0) {
            color = Color.parseColor("#27ae60");
        }
        else {
            color = Color.parseColor("#c0392b");
        }

        holder.symbol.setText(mDataset.get(position).getSymbol());

        // Format and display price and day change
        holder.price.setText(mDataset.get(position).getFormattedPrice());
        holder.change.setText(mDataset.get(position).getFormattedChange());
        holder.change.setTextColor(color);

        holder.dayRange.setText(dItem.getFormattedDayLow() + " - " + dItem.getFormattedDayHigh());
        holder.yearRange.setText(dItem.getFormattedYearLow() + " - " + dItem.getFormattedYearHigh());

        if (dropdownIndicator[position]) {
            holder.expandedInfo.setVisibility(View.VISIBLE);
        }
        else {
            holder.expandedInfo.setVisibility(View.GONE);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownIndicator[position] = !dropdownIndicator[position];
                notifyDataSetChanged();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}