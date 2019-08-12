package com.amith.schoollabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

        //View view = View.inflate(getBaseContext(), R.layout.activity_chemicals, toolbar);
//        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
//            return new ItemViewHolder(view);
//        }

        //View view = inflater.inflate(R.layout.fragment_user, container, false);

//        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        @NonNull
//        @Override
//        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
//            return new ItemViewHolder(view);
//        }
//
//
//        View view = View.inflate(R.layout.activity_chemicals, parent, false);
        //View view = View.inflate
//        View view = View.inflate(getBaseContext(), R.layout.activity_chemicals, toolbar);
//
//
        recyclerView = (RecyclerView) findViewById(R.id.recycler_chemical_view);
        recyclerView.setHasFixedSize(true);

        //layoutManager = new LinearLayoutManager(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        chemicalAdapter = new ChemicalAdapter(getBaseContext(), mChemicals);
//        recyclerView.setAdapter(chemicalAdapter);
        mChemicals = new ArrayList<>();

       readChemicals();

        //return view;

    }

    private void readChemicals() {

        Toast.makeText(ChemicalsActivity.this.getBaseContext(), "0", Toast.LENGTH_SHORT);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chemicals");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mChemicals.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chemical chemical = snapshot.getValue(Chemical.class);

                    Toast.makeText(ChemicalsActivity.this.getBaseContext(), "1", Toast.LENGTH_SHORT);
                    assert chemical != null;
//                    Log.d("dsa", ""+chemical.getItem_name());
                      mChemicals.add(chemical);
                    Toast.makeText(ChemicalsActivity.this.getBaseContext(), "2", Toast.LENGTH_LONG);
                }
//                mChemicals.add(new Chemical("dsad","chem name", (long)2,(long)2.0,"g"));

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
