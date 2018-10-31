package com.example.ishaycena.tabfragments.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * recycler view adapter
 *
 * @param <T> data extends AbsLostFound
 * @param <U> data represented in the adapter extends AbsRecyclerViewAdapterItem
 */
public abstract class AbsRecyclerViewAdapter<T extends AbsLostFound, U extends AbsRecyclerViewAdapterItem>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "AbsRecyclerViewAdapter";

    protected class ItemTypes {
        protected static final int NORMAL = 0, FOOTER = 1;
    }

    // vars
    protected Context context;
    public ArrayList<U> mLstData;
    protected int lastItemPosition = -1;

    @Override
    public int getItemViewType(int position) {
        if (mLstData.get(position) != null) {
            return ItemTypes.NORMAL;
        } else {
            return ItemTypes.FOOTER;
        }
    }

    public AbsRecyclerViewAdapter(Context context, ArrayList<U> data) {
        this.context = context;
        this.mLstData = data;
    }

    /**
     * adds a fetched item to the adapter
     * @param lstData list of data which extends AbsRecyclerViewAdapterItem
     */
    public abstract void addFetchedItem(List<T> lstData);

    public void addItemWithHandler(U item) {
        mLstData.add(item);
    }
}
