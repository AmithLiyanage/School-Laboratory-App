package com.amith.schoollabapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.Adapter.RecycleViewAdapter;
import com.amith.schoollabapp.Model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    List<MenuItem> firstItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("RECYCLER VIEW");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this, "OK", Toast.LENGTH_SHORT ).show();

        firstItem = new ArrayList<>();
        firstItem.add(new MenuItem("Glassware", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Chemicals", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Perishables", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Permanent Eq", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Glassware", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Chemicals", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Perishables", "'Equipments", "Description", R.drawable.ic_menu_camera));
        firstItem.add(new MenuItem("Permanent Eq", "'Equipments", "Description", R.drawable.ic_menu_camera));

        try {
            Toast.makeText(this, "IN the Recycler view", Toast.LENGTH_SHORT ).show();
            RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view);
            RecycleViewAdapter myAdapter = new RecycleViewAdapter(this, firstItem);
            myrv.setLayoutManager(new GridLayoutManager(this,3));
            myrv.setAdapter(myAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Recycler error : "+e, Toast.LENGTH_LONG ).show();
        }

    }
}

