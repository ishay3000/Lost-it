package com.example.ishaycena.tabfragments.FoundService;

import android.util.Log;

import com.example.ishaycena.tabfragments.Utilities.IDataFetcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoundFetcher implements IDataFetcher {
    @Override
    public void FetchData() {
        try {
            mDatabaseReference
                    .orderByKey()
                    .startAt(mOldestPostId)
                    .limitToFirst(10)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d(TAG, "onDataChange: fetched items count: " + dataSnapshot.getChildrenCount());

                            for (DataSnapshot snapshot :
                                    dataSnapshot.getChildren()) {
                                if (!mOldestPostId.equals(snapshot.getKey())) {
                                    Found data = snapshot.getValue(Found.class);
                                    mLst.add(data);

                                    // save the new oldest retrieved found id
                                    mOldestPostId = snapshot.getKey();
                                }
                            }
                            Log.d(TAG, "onDataChange: list size with limit: " + mLst.size());
                            OnDataFetched();
                        }

//                    Log.d(TAG, "onDataChange: finished retrieving data");


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        } catch (Exception ignored) {

        }
    }

    @Override
    public void OnDataFetched() {
        mListener.onDataFetched(mLst, mOldestPostId);
    }

    public interface OnDataFetchedListener {
        /**
         * triggers when data is fetched from the database
         *
         * @param founds               ArrayList of Founds, with new data
         * @param changedOldestFoundId the new oldest post ID
         */
        void onDataFetched(final List<Found> founds, String changedOldestFoundId);
    }

    private static final String TAG = "FoundFetcher";
    private FoundFetcher.OnDataFetchedListener mListener;
    private DatabaseReference mDatabaseReference;
    private String mOldestPostId;
    private List<Found> mLst = new ArrayList<>();

    public FoundFetcher(OnDataFetchedListener mListener, DatabaseReference mDatabaseReference, String mOldestPostId) {
        this.mListener = mListener;
        this.mDatabaseReference = mDatabaseReference;
        this.mOldestPostId = mOldestPostId;
    }
}
