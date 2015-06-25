package com.palindromicstudios.stockapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Natan on 6/25/2015.
 */
public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder> {
    private StockQuote[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView symbol;
        public TextView price;
        public ViewHolder(View v) {
            super(v);
            symbol = (TextView) v.findViewById(R.id.symbol);
            price = (TextView) v.findViewById(R.id.price);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TickerAdapter(StockQuote[] myDataset) {
        mDataset = myDataset;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.symbol.setText(mDataset[position].getSymbol());
        holder.price.setText(String.valueOf(mDataset[position].getPrice()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}