package com.example.ishaycena.tabfragments.FoundService;

import android.util.Log;

import com.example.ishaycena.tabfragments.Utilities.AbsItem;
import com.example.ishaycena.tabfragments.Utilities.IDataFetcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * fetches data from firebase
 *
 * @param <T> class extends AbsItems (either lost or found)
 */
public class DataFetcher<T extends AbsItem> implements IDataFetcher {
    public interface OnDataFetchedListener {
        /**
         * triggers when data is fetched from the database
         *
         * @param data                ArrayList of data, with new data
         * @param changedOldestDataId the new oldest post ID
         */
        void onDataFetched(final List<? extends AbsItem> data, String changedOldestDataId);
    }

    //#region vars
    private static final String TAG = "DataFetcher";
    private DataFetcher.OnDataFetchedListener mListener;
    private DatabaseReference mDatabaseReference;
    private String mOldestPostId;
    private List<T> mLst = new ArrayList<>();
    private Class<T> mDataType;
    //#endregion


    //#region ctor
    public DataFetcher(DataFetcher.OnDataFetchedListener mListener, Class<T> dataType, DatabaseReference mDatabaseReference, String mOldestPostId) {
        this.mListener = mListener;
        this.mDatabaseReference = mDatabaseReference;
        this.mOldestPostId = mOldestPostId;
        mDataType = dataType;
    }
    //#endregion


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
                                    T data = snapshot.getValue(mDataType);
                                    mLst.add(data);

                                    // save the new oldest retrieved found id
                                    mOldestPostId = snapshot.getKey();
                                }
                            }
                            Log.d(TAG, "onDataChange: list size with limit: " + mLst.size());
                            OnDataFetched();
                        }

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
}
