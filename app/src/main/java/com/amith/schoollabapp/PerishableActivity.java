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

import com.amith.schoollabapp.Adapter.PerishableAdapter;
import com.amith.schoollabapp.Model.Perishable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerishableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PerishableAdapter perishableAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Perishable> mPerishable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perishable);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perishables");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PerishableActivity.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
            }
        });
//
        recyclerView = (RecyclerView) findViewById(R.id.recycler_perishable_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mPerishable = new ArrayList<>();

        readChemicals();

    }

    private void readChemicals() {

        Toast.makeText(PerishableActivity.this.getBaseContext(), "0", Toast.LENGTH_SHORT);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("perishables");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mPerishable.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Perishable chemical = snapshot.getValue(Perishable.class);

                    Toast.makeText(PerishableActivity.this.getBaseContext(), "1", Toast.LENGTH_SHORT);
                    assert chemical != null;
//                    Log.d("dsa", ""+chemical.getItem_name());
                    mPerishable.add(chemical);
                    Toast.makeText(PerishableActivity.this.getBaseContext(), "2", Toast.LENGTH_LONG);
                }
//                mChemicals.add(new Chemical("dsad","chem name", (long)2,(long)2.0,"g"));

                try {
                    perishableAdapter = new PerishableAdapter(getBaseContext(), mPerishable);
                    recyclerView.setAdapter(perishableAdapter);
                } catch (Exception e) {
                    Toast.makeText(PerishableActivity.this.getBaseContext(), "Perishable loading Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
