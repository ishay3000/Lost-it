package com.example.ishaycena.tabfragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ishaycena.tabfragments.Utilities.AdapterFound;
import com.example.ishaycena.tabfragments.Utilities.RecyclerViewAdapter;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    // widgets
    RecyclerView recyclerView;

    // vars
    RecyclerViewAdapter adapter;
    ArrayList<AdapterFound> lstFounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        adapter = new RecyclerViewAdapter(this, lstFounds);
        recyclerView = findViewById(R.id.recyclerview_2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bitmap profile = BitmapFactory.decodeResource(getResources(),
                R.drawable.ishay_1);
        Bitmap badge = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_crown);
        Bitmap map = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_map);
        Bitmap item = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_passport);

        String name = "Ishay Cena", description = "AdapterFound this passport near the Town Hall...";
        AdapterFound found = new AdapterFound(profile, badge, item, map, name, description);

        adapter.addItem(found);
    }
}
