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

import com.amith.schoollabapp.Adapter.GlasswareAdapter;
import com.amith.schoollabapp.Model.Glassware;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GlasswareActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GlasswareAdapter glasswareAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Glassware> mGlassware;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glassware);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Glassware");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GlasswareActivity.this, NavigationActivity.class));//.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_glassware_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mGlassware = new ArrayList<>();

        mGlassware = new ArrayList<>();

        readGlassware();

    }

    private void readGlassware() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("glassware");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGlassware.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Glassware glassware = snapshot.getValue(Glassware.class);

                    assert glassware != null;
                    mGlassware.add(glassware);
                    //Toast.makeText(getApplicationContext(), glassware.getId(), Toast.LENGTH_LONG).show();
                }

                try {
                    glasswareAdapter = new GlasswareAdapter(getBaseContext(), mGlassware);
                    recyclerView.setAdapter(glasswareAdapter);
                } catch (Exception e) {
                    Toast.makeText(GlasswareActivity.this.getBaseContext(), "Chemical loading Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}