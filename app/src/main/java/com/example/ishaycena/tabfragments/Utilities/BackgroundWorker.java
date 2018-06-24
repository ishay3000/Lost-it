package com.example.ishaycena.tabfragments.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BackgroundWorker extends AsyncTask<Integer, Found, List<Found>> {
    private static final String TAG = "BackgroundWorker";

    public interface OnDataFetchedListener {
        void onDataFetched(List<Found> founds);
    }

    private OnDataFetchedListener listener;

    public BackgroundWorker(OnDataFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Found> doInBackground(Integer... integers) {
        List<Found> lst = new ArrayList<>();
        // lst.add()...
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.d(TAG, "doInBackground: sleeping...");
        }

        return lst;
    }

    @Override
    protected void onPostExecute(List<Found> founds) {
        listener.onDataFetched(founds);
//        ArrayList<Found> lst = adapter.lstFounds;
//        int size = lst.size();
//
//        lst.remove(size - 1);
////                    Bitmap profile = BitmapFactory.decodeResource( getResources(),
////                    R.drawable.ishay_1);
////            Bitmap badge = BitmapFactory.decodeResource(getResources(),
////                    R.drawable.ic_crown);
////            Bitmap map = BitmapFactory.decodeResource(getResources(),
////                    R.drawable.ic_map);
////            Bitmap item = BitmapFactory.decodeResource(getResources(),
////                    R.drawable.ic_passport);
////
////            String name = "Ishay Cena", description = "Found this passport near the Town Hall...";
////            Found found2 = new Found(profile, badge, item, map, name, description);
//
////            adapter.addItem(found2);
//        adapter.notifyItemRangeChanged(size - 1, lst.size() - size);
//        response.onResponse();
    }
}
