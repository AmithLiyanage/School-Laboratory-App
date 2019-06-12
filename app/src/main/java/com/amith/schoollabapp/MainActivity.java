package com.amith.schoollabapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    ImageView imageView;
    FirebaseUser firebaseUser;
    DatabaseReference referenceCanAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.loading_presentage_txt);
        imageView = findViewById(R.id.smart_lab_logo);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

//        progressAnimation();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        referenceCanAccess = FirebaseDatabase.getInstance().getReference("Users");//.child(firebaseUser.toString()).child("approve")

        //for check if user is null
        if (firebaseUser != null) {
            Log.v("Login Activity", "User is not null" + firebaseUser.getDisplayName());
            //Toast.makeText(LoginActivity.this, "User status : "+referenceCanAccess, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
        }
        else {
            Log.v("Login Activity", "User is null");
            startActivity(new Intent(this, LoginActivity.class));//HomeActivity
        }
    }

    public void progressAnimation () {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(6000);
        progressBar.setAnimation(anim);
        //if (value == to) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        referenceCanAccess = FirebaseDatabase.getInstance().getReference("Users");//.child(firebaseUser.toString()).child("approve")

        //for check if user is null
        if (firebaseUser != null) {
            Log.v("Login Activity", "User is not null" + firebaseUser.getDisplayName());
            //Toast.makeText(LoginActivity.this, "User status : "+referenceCanAccess, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
        }
        else {
            Log.v("Login Activity", "User is null");
            startActivity(new Intent(this, LoginActivity.class));//HomeActivity
        }
    }
}

