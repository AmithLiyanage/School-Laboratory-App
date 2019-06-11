package com.amith.schoollabapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText email, password;
    FirebaseAuth auth;
    DatabaseReference referenceCanAccess;

    FirebaseUser firebaseUser;
    TextView forget_password;
    Button loginButton, signButton;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        referenceCanAccess = FirebaseDatabase.getInstance().getReference("Users");//.child(firebaseUser.toString()).child("approve")
//
//        //for check if user is null
//        if(firebaseUser != null){
//            Log.v("Login Activity","User is not null" + firebaseUser.getDisplayName());
//            //Toast.makeText(LoginActivity.this, "User status : "+referenceCanAccess, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        Log.v("Login Activity","User is null");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LOGIN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_btnLogin);   //Login Button
        signButton = findViewById(R.id.login_btnSignUp);  //SignUp Button
        forget_password = findViewById(R.id.forget_password);

//        forget_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//            }
//        });

        // to Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    try {
                                        if (task.isSuccessful()) {
                                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                            assert firebaseUser != null;
                                            Log.v("Login Activity","Fuser : " + firebaseUser.getDisplayName());
                                            Log.v("Login Activity","Fuser : " + firebaseUser.getUid());
//                                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
                                            authStateListener.onAuthStateChanged(FirebaseAuth.getInstance());

                                        } else {
                                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login error : "+e, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        //to open SignUp
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
////                    Intent I = new Intent(ActivityLogin.this, UserActivity.class);
////                    startActivity(I);
                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }



}