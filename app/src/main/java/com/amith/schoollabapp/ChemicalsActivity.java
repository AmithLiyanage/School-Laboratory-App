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

import com.amith.schoollabapp.Adapter.ChemicalAdapter;
import com.amith.schoollabapp.Model.Chemical;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChemicalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChemicalAdapter chemicalAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Chemical> mChemicals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemicals);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chemicals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChemicalsActivity.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
            }
        });

        View view = View.inflate(getBaseContext(), R.layout.activity_chemicals, toolbar);
        //View view = View.inflate(getBaseContext(), R.layout.activity_chemicals, recyclerView);
        recyclerView = view.findViewById(R.id.recycler_chemical_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mChemicals = new ArrayList<>();

        readChemicals();

    }

    private void readChemicals() {

        Toast.makeText(ChemicalsActivity.this.getBaseContext(), "0", Toast.LENGTH_SHORT);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("glassware");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mChemicals.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chemical chemical = snapshot.getValue(Chemical.class);

                    Toast.makeText(ChemicalsActivity.this.getBaseContext(), "1", Toast.LENGTH_SHORT);
                    assert chemical != null;
                    mChemicals.add(chemical);
                    Toast.makeText(ChemicalsActivity.this.getBaseContext(), "2", Toast.LENGTH_LONG);
                }

                try {
                    chemicalAdapter = new ChemicalAdapter(getBaseContext(), mChemicals);
                    recyclerView.setAdapter(chemicalAdapter);
                } catch (Exception e) {
                    Toast.makeText(ChemicalsActivity.this.getBaseContext(), "Chemical loading Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
