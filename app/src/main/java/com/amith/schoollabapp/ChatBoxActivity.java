package com.amith.schoollabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.amith.schoollabapp.Fragment.ChatFragment;
import com.amith.schoollabapp.Fragment.UserFragment;
import com.amith.schoollabapp.Model.User;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatBoxActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        Toolbar toolbar = findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatBoxActivity.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
            }
        });

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);//error point
                    username.setText(user.getUsername());

                    try {
                        if(user.getImageURL().equals("default")){
                            profile_image.setImageResource(R.drawable.pp_icon);
                        } else {

                            // change here(14 th video)
                            Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                        }

                    } catch (Exception e) {
                        System.out.println("pic : " + e);
                        Toast.makeText(ChatBoxActivity.this, "picture error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()); //move to here

        viewPagerAdapter.addFragment(new ChatFragment(), "Chat");
        viewPagerAdapter.addFragment(new UserFragment(), "Users");


        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    //new for super in ViewPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        @SuppressWarnings("deprecation")
        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        //Ctrl + O
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
