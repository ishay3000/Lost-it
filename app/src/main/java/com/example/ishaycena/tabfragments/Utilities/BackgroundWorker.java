package com.example.ishaycena.tabfragments.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BackgroundWorker extends AsyncTask<Integer, Found, List<Found>> {
    private static final String TAG = "BackgroundWorker";

    public interface OnDataFetchedListener {
        /**
         * triggers when data is fetched from the database
         *
         * @param founds       ArrayList of Founds, with new data
         * @param oldestPostId the new oldest post ID
         */
        void onDataFetched(List<Found> founds, String oldestPostId);
    }

    private OnDataFetchedListener listener;
    private DatabaseReference mDatabaseReference;
    private String mOldestPostId;

    public BackgroundWorker(OnDataFetchedListener listener, DatabaseReference reference, String oldestPostId) {
        this.listener = listener;
        mDatabaseReference = reference;
        mOldestPostId = oldestPostId;
    }

    @Override
    protected List<Found> doInBackground(Integer... integers) {
        final List<Found> lst = new ArrayList<>();
        /*
        for (DataSnapshot snapShot : dataSnapshot.getChildren()){
            Found found = ...

            lst.add
        }
         */
        // lst.add()...
        try {
            Thread.sleep(1000);

            mDatabaseReference.orderByKey().startAt(mOldestPostId).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                        Found found = snapshot.getValue(Found.class);
                        lst.add(found);

                        mOldestPostId = snapshot.getKey();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (InterruptedException e) {
            Log.d(TAG, "doInBackground: sleeping...");
        }

        return lst;
    }

    @Override
    protected void onPostExecute(List<Found> founds) {
        listener.onDataFetched(founds, mOldestPostId);
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
