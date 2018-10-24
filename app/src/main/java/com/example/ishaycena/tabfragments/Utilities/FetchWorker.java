package com.example.ishaycena.tabfragments.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FetchWorker<T> extends AsyncTask<Void, T, List<T>> {
    public interface OnDataFetchedListener<T> {
        /**
         * triggers when data is fetched from the database
         *
         * @param founds               ArrayList of Founds, with new data
         * @param changedOldestFoundId the new oldest post ID
         */
        void onDataFetched(final List<T> founds, String changedOldestFoundId);
    }

    private static final String TAG = "FetchWorker";
    private FetchWorker.OnDataFetchedListener<T> mListener;
    private DatabaseReference mDatabaseReference;
    private String mOldestPostId;
    private Class<T> mType;
    private List<T> mLst = new ArrayList<>();


    /**
     * async task which fetches T data from firebase
     *
     * @param listener     mListener for the data
     * @param type         the type of data to receive
     * @param reference    firebase db ref
     * @param oldestPostId oldest post id to start with
     */
    public FetchWorker(FetchWorker.OnDataFetchedListener<T> listener, Class<T> type, DatabaseReference reference, String oldestPostId) {
        this.mListener = listener;
        mDatabaseReference = reference;
        mOldestPostId = oldestPostId;
        mType = type;
    }

    @Override
    protected synchronized List<T> doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: starting database fetching");

/*        try {
            mDatabaseReference.orderByKey()
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()) {
                                T data = snapshot.getValue(mType);
                                mLst.add(data);

                                // save the new oldest retrieved found id
                                mOldestPostId = snapshot.getKey();
                            }
                            Log.d(TAG, "onDataChange: list size: " + mLst.size());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }catch (Exception ignored){

        }*/


        // fetching from database
        try {
            mDatabaseReference.orderByKey().startAt(mOldestPostId).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.d(TAG, "onDataChange: fetched items count: " + dataSnapshot.getChildrenCount());

                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {

                        T data = snapshot.getValue(mType);
                        mLst.add(data);

                        // save the new oldest retrieved found id
                        mOldestPostId = snapshot.getKey();
                    }
                    Log.d(TAG, "onDataChange: list size with limit: " + mLst.size());
                }

//                    Log.d(TAG, "onDataChange: finished retrieving data");


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception ignored) {

        }
        return mLst;
    }

    @Override
    protected synchronized void onPostExecute(List<T> ts) {
        mListener.onDataFetched(ts, mOldestPostId);
    }
}
