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
import com.example.ishaycena.tabfragments.Utilities.HighScoresItem;
import com.example.ishaycena.tabfragments.Utilities.RecyclerViewHighScoresAdapter;

import java.util.ArrayList;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    // important global var
    View view;

    // views
    private RecyclerView recyclerView;

    // vars
    private ArrayList<HighScoresItem> lstHighScores = new ArrayList<>();
    private RecyclerViewHighScoresAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.tab3_fragment, container, false);

            Log.d(TAG, "onCreateView: started fragment!");

            recyclerView = view.findViewById(R.id.recyclerview_tab3);

            initFounds();

            Bitmap profile = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ishay_1);
            Bitmap badge = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_crown);

            String personName = "Ishay Muchtar";
            int foundsCount, lostsCount;
            foundsCount = 6;
            lostsCount = 5;

            HighScoresItem ishay = new HighScoresItem(profile, badge, personName, 6, 5);
            HighScoresItem aviad = new HighScoresItem(profile, badge, "Aviad", 4, 5);
            HighScoresItem avichay = new HighScoresItem(profile, badge, "Avichay", 3, 5);


            adapter.addMultipleItems(ishay, aviad, avichay);
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
//        lstFounds.add(found);

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        adapter = new RecyclerViewHighScoresAdapter(getContext(), lstHighScores);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
