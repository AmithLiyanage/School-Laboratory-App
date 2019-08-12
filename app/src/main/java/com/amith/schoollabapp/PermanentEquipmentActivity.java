package com.amith.schoollabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amith.schoollabapp.Adapter.PermanentEquipmentAdapter;
import com.amith.schoollabapp.Model.PermanentEquipment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.ArrayList;

public class PermanentEquipmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PermanentEquipmentAdapter permanentEquipmentAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<PermanentEquipment> mPermanentEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permanent_equipment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Permanent Equipments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PermanentEquipmentActivity.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
            }
        });
//
        recyclerView = (RecyclerView) findViewById(R.id.recycler_permanent_equipment_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mPermanentEquipment = new ArrayList<>();

        readPermanentEquipment();

    }

    private void readPermanentEquipment() {

        Toast.makeText(PermanentEquipmentActivity.this.getBaseContext(), "0", Toast.LENGTH_SHORT);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("permenant_equipment");//dont change spellings, thees fron  DB

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mPermanentEquipment.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PermanentEquipment chemical = snapshot.getValue(PermanentEquipment.class);

                    Toast.makeText(PermanentEquipmentActivity.this.getBaseContext(), "1", Toast.LENGTH_SHORT);
                    assert chemical != null;
//                    Log.d("dsa", ""+chemical.getItem_name());
                    mPermanentEquipment.add(chemical);
                    Toast.makeText(PermanentEquipmentActivity.this.getBaseContext(), "2", Toast.LENGTH_LONG);
                }
//                mChemicals.add(new Chemical("dsad","chem name", (long)2,(long)2.0,"g"));

                try {
                    permanentEquipmentAdapter = new PermanentEquipmentAdapter(getBaseContext(), mPermanentEquipment);
                    recyclerView.setAdapter(permanentEquipmentAdapter);
                } catch (Exception e) {
                    Toast.makeText(PermanentEquipmentActivity.this.getBaseContext(), "PermanentEquipment loading Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}