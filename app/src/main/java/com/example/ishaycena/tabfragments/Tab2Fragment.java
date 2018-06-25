package com.example.ishaycena.tabfragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.Utilities.BackgroundWorker;
import com.example.ishaycena.tabfragments.Utilities.Found;
import com.example.ishaycena.tabfragments.Utilities.RecyclerViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Tab2Fragment extends Fragment implements BackgroundWorker.OnDataFetchedListener {
    private static final String TAG = "Tab2Fragment";
    View view;

//    @Override
//    public void onDataFetched(final List<Found> founds, String changedOldestFoundId) {
//        // save the new oldest found id
//        this.oldestFoundId = changedOldestFoundId;
//
//        Bitmap profile = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ishay_1);
//        Bitmap badge = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_crown);
//        Bitmap map = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_map);
//        Bitmap item = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_passport);
//
//        String name = "Ishay Cena", description = "Found this passport near the Town Hall...";
//        final Found found2 = new Found(profile, badge, item, map, name, description);
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                int size = adapter.lstFounds.size();
//
//                adapter.lstFounds.remove(adapter.lstFounds.size() - 1);
//                adapter.notifyItemRemoved(size - 1);
//                adapter.notifyItemRangeChanged(size - 1, adapter.lstFounds.size() - size);
//                for (Found found :
//                        founds) {
//                    adapter.addItem(found);
//                }
//
//                adapter.addItemWithHandler(found2);
//                adapter.addItemWithHandler(found2);
//                adapter.addItemWithHandler(found2);
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//
//        // finished loading
//        loading = false;
//    }

    // views
    private RecyclerView recyclerView;

    // firebase
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    String oldestFoundId = "";


    // vars
    private ArrayList<Found> lstFounds = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    // recycler view vars
    LinearLayoutManager mLayoutManager;
    private boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.tab2_fragment, container, false);

            Log.d(TAG, "onCreateView: started fragment!");

            recyclerView = view.findViewById(R.id.recyclerview_tab2);

            initRecyclerView();

            initFirebase();

            Bitmap profile = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ishay_1);
            Bitmap badge = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_crown);
            Bitmap map = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_map);
            Bitmap item = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_passport);

            String name = "Ishay Cena", description = "Found this passport near the Town Hall...";
            final Found found2 = new Found(profile, badge, item, map, name, description);

//            adapter.addItem(found2);
//            adapter.addItem(found2);
//            adapter.addItem(found2);
//            adapter.addItem(found2);
//            adapter.addItem(found2);

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 6; i++) {
                        adapter.addItemWithHandler(found2);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }

        return view;
    }

    /**
     * init firebase references
     */
    private void initFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Founds");
    }

    @Override
    public void onDataFetched(final List<Found> founds, String changedOldestFoundId) {
        // save oldest post id
        oldestFoundId = changedOldestFoundId;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int size = adapter.lstFounds.size();
                // remove progress bar (last item in the adapter)
                adapter.lstFounds.remove(adapter.lstFounds.size() - 1);
                adapter.notifyItemRemoved(size - 1);
                adapter.notifyItemRangeChanged(size - 1, adapter.lstFounds.size() - size);
                for (Found found :
                        founds) {
                    adapter.addItem(found);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    public class OnVerticalScrollListener
            extends RecyclerView.OnScrollListener {
        private Tab2Fragment context;

        public OnVerticalScrollListener(Tab2Fragment ctx) {
            this.context = ctx;
        }

        @Override
        public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(-1)) {
                onScrolledToTop();
            } else if (!recyclerView.canScrollVertically(1)) {
                onScrolledToBottom();
            } else if (dy < 0) {
                onScrolledUp();
            } else if (dy > 0) {
                onScrolledDown();
            }
        }

        public void onScrolledUp() {
        }

        public void onScrolledDown() {
        }

        public void onScrolledToTop() {
        }

        public void onScrolledToBottom() {
            //                Toast.makeText(getContext(), "Bottom page, last item id is: " + totalItemCount, Toast.LENGTH_SHORT).show();
            if (!loading) {
                totalItemCount = mLayoutManager.getItemCount();

                Toast.makeText(getContext(), "Reached bottom", Toast.LENGTH_SHORT).show();

                // add progress bar to the bottom of the page
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: adding progress bar");
                        adapter.addItemWithHandler(null);
                        adapter.notifyDataSetChanged();
                    }
                });

                // set loading to true
                loading = true;

//                BackgroundWorker worker = new BackgroundWorker(Tab2Fragment.this, mDatabaseReference, oldestFoundId);
//                worker.execute();
            }
//            Bitmap profile = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.ishay_1);
//            Bitmap badge = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.ic_crown);
//            Bitmap map = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.ic_map);
//            Bitmap item = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.ic_passport);
//
//            String name = "Ishay Cena", description = "Found this passport near the Town Hall...";
//            Found found2 = new Found(profile, badge, item, map, name, description);
//
//            adapter.addItem(found2);
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        adapter = new RecyclerViewAdapter(getContext(), lstFounds);
        recyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnScrollListener(new OnVerticalScrollListener(this));
    }
}

