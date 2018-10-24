package com.example.ishaycena.tabfragments.MainService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ishaycena.tabfragments.R;
import com.example.ishaycena.tabfragments.Utilities.Found;
import com.example.ishaycena.tabfragments.Utilities.RecyclerViewAdapter;

import java.util.ArrayList;

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";
    // important global var
    View view;

    // views
    private RecyclerView recyclerView;

    // vars
    private ArrayList<Found> lstFounds = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            // inflate (show) the fragment layout on the screen
            view = inflater.inflate(R.layout.tab1_fragment, container, false);

            Log.d(TAG, "onCreateView: started fragment!");

            recyclerView = view.findViewById(R.id.recyclerview_tab1);

            initFounds();

            Bitmap profile = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ishay_1);
            Bitmap badge = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_crown);
            Bitmap map = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_map);
            Bitmap item = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_passport);

            String name = "Ishay Cena", description = "Found this passport near the Town Hall...";
            Found found2 = new Found(profile, badge, item, map, name, description);

            adapter.addItem(found2);
        }

        return view;
    }


    private void initFounds(){
//        Bitmap profile = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ishay_1);
//        Bitmap badge = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_crown);
//        Bitmap map = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_map);
//        Bitmap item = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_passport);
//
//        String name = "Ishay Muchtar", description = "Found this passport near the Town Hall!";
//        Found found = new Found(profile, badge, item, map, name, description);
//
//        mLstFounds.add(found);
        
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        adapter = new RecyclerViewAdapter(getContext(), lstFounds);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
