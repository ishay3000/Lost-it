package com.example.ishaycena.tabfragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.Utilities.Found;
import com.example.ishaycena.tabfragments.Utilities.RecyclerViewAdapter;

import java.util.ArrayList;

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
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
            view = inflater.inflate(R.layout.tab2_fragment, container, false);

            Log.d(TAG, "onCreateView: started fragment!");

            recyclerView = view.findViewById(R.id.recyclerview_tab2);
//        AnimationSet set = new AnimationSet(true);
//
//        Animation animation = new AlphaAnimation(0.0f, 1.0f);
//        animation.setDuration(500);
//        set.addAnimation(animation);
//
//        animation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
//        );
//        animation.setDuration(100);
//        set.addAnimation(animation);
//
//        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
//
//        recyclerView.setLayoutAnimation(controller);

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
            adapter.addItem(found2);
            adapter.addItem(found2);
            adapter.addItem(found2);
            adapter.addItem(found2);
//            adapter.addItem(found2);
//            adapter.addItem(found2);
//            adapter.addItem(found2);
//            adapter.addItem(found2);
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

        adapter = new RecyclerViewAdapter(getContext(), lstFounds);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

//                Toast.makeText(getActivity(), "onScrolledStateChanged called", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onScrollStateChanged: called");
            }

            public void slideUp(View view){
                view.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,                 // fromXDelta
                        0,                 // toXDelta
                        view.getHeight(),  // fromYDelta
                        0);                // toYDelta
                animate.setDuration(100);
                animate.setFillAfter(true);
                view.startAnimation(animate);
            }

            // slide the view from its current position to below itself
            public void slideDown(View view){
                TranslateAnimation animate = new TranslateAnimation(
                        0,                 // fromXDelta
                        0,                 // toXDelta
                        0,                 // fromYDelta
                        view.getHeight()); // toYDelta
                animate.setDuration(100);
                animate.setFillAfter(true);
                view.startAnimation(animate);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                Toast.makeText(getActivity(), "onScrolled called", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onScrolled: called");

                if (dy > 0 && bottomNavigationView.isShown()) {
//                    bottomNavigationView.setVisibility(View.GONE);
                    slideDown(bottomNavigationView);
                } else if (dy < 0 ) {
//                    bottomNavigationView.setVisibility(View.VISIBLE);
                    slideUp(bottomNavigationView);
                }
            }
            
        });
    }
}

