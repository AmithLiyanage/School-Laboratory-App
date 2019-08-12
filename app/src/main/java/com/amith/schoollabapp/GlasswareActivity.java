package com.amith.schoollabapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amith.schoollabapp.Adapter.ChemicalAdapter;
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

    private RecyclerView recyclerView_s;
    private RecyclerView.Adapter mAdapter_s;
    private RecyclerView.LayoutManager layoutManager_s;

    public ListView glasswareList;

    //TextView chemicalName, quantity, measurement;

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

        glasswareList = (ListView)findViewById(R.id.glassware_list_view);

        mGlassware= new ArrayList<>();

        //   readChemicals();
        getGlassware();

    }

//    private void readGlassware() {
//
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("glassware");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mChemicals.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Chemical chemical = snapshot.getValue(Chemical.class);
//
//                    assert chemical != null;
//                    mChemicals.add(chemical);
//                    Toast.makeText(getApplicationContext(), chemical.getId(), Toast.LENGTH_LONG).show();
//                }
//
//                try {
//                    chemicalAdapter = new ChemicalAdapter(getBaseContext(), mChemicals);
//                    recyclerView.setAdapter(chemicalAdapter);
//                } catch (Exception e) {
//                    Toast.makeText(ChemicalsActivity.this.getBaseContext(), "Chemical loading Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void getData(){
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("glassware");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Glassware chemical_item = snapshot.getValue(Glassware.class);
                    Toast.makeText(getApplicationContext(), chemical_item.getGlasswareName(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void getGlassware() {

        Toast.makeText(GlasswareActivity.this.getBaseContext(), "0", Toast.LENGTH_SHORT);
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("glassware");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mGlassware.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Glassware glassware = snapshot.getValue(Glassware.class);

                    Toast.makeText(GlasswareActivity.this.getBaseContext(), "1", Toast.LENGTH_SHORT);
                    assert glassware != null;
                    mGlassware.add(glassware);
                    Toast.makeText(GlasswareActivity.this.getBaseContext(), "2", Toast.LENGTH_LONG);
                }

                try {
//                    mAdapter_s = new ChemicalAdapter(getBaseContext(), mChemicals);
//                    recyclerView_s.setAdapter(chemicalAdapter);

                    AdapterGlassware glasswareAdapter = new AdapterGlassware(GlasswareActivity.this, mGlassware);
                    glasswareList.setAdapter(glasswareAdapter);
                } catch (Exception e) {
                    Toast.makeText(GlasswareActivity.this.getBaseContext(), "Glassware loading Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class AdapterGlassware extends ArrayAdapter<Glassware> {

        //        private List<String> UserName;
//        private List<String> DiscussionType;
//        private List<String> DiscussionBody;
//        private List<String> DocumentID;
        //private ArrayList<Product> Products;
        //private ArrayList<Chemical> Products = new ArrayList<Chemical>();




        private Activity context;
        private List<Glassware> mGlassware;

        //adapter constructor
        private AdapterGlassware(Activity context, List<Glassware> mGlassware) {
            super(context, R.layout.activity_glassware, mGlassware);
            this.context = context;
            this.mGlassware = mGlassware;

        }



        @NonNull
        @Override

        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
            View r = convertView;
            AdapterGlassware.ViewHolder viewHolder = null;

            //things to do onclick of an item
            if(r==null){
                LayoutInflater layoutInflater = context.getLayoutInflater();
                r = layoutInflater.inflate(R.layout.glassware_item,null,true);

                viewHolder = new AdapterGlassware.ViewHolder(r);
                r.setTag(viewHolder);
            }else{
                viewHolder = (AdapterGlassware.ViewHolder)r.getTag();
            }



            //bind data to UI components
            Glassware glassware = mGlassware.get(position);
            viewHolder.glassware_name.setText(glassware.getGlasswareName());
            viewHolder.available.setText(glassware.getAvailable().toString());
            viewHolder.measurement.setText(glassware.getMeasurement());

            return r;

        }

        //Defining UI components
        class ViewHolder{

            public TextView glassware_name;
            public TextView available;
            public TextView measurement;

            public ViewHolder(View v) {

                glassware_name = v.findViewById(R.id.glassware_name);
                available = v.findViewById(R.id.glassware_avaliable);
                measurement = v.findViewById(R.id.glassware_measurement);
            }


        }
    }
}
