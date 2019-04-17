package com.amith.schoollabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText userName, email, password, cpassword, phoneNumber;
    Button btnSignUp;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SIGNUP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(SignUpActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
//            }
//        });

        userName = findViewById(R.id.signup_userName);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        cpassword = findViewById(R.id.signup_confirmPassword);
        phoneNumber = findViewById(R.id.signup_phoneNumber);
        btnSignUp =  findViewById(R.id.signup_btnSignUp);  //SignUp Button

        //try{
        auth = FirebaseAuth.getInstance();
//        } catch (Exception e) {
//            System.out.println("Exe : "+e);
//            Toast.makeText(this, "E : "+e, Toast.LENGTH_LONG).show();
//        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = userName.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_cpassword = cpassword.getText().toString();
                String txt_phoneNumber = password.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_phoneNumber) || TextUtils.isEmpty(txt_cpassword)){
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length()<8){
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else if (!(txt_cpassword.contentEquals(txt_password))){
                    Toast.makeText(SignUpActivity.this, "Password and Confirm Password are not match", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
    }

    private void register(final String username, String email, String password){ // String phoneNumber not dded
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        String userId = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", userId);
                        hashMap.put("username", username);
                        hashMap.put("imageURL", "default");
//                        hashMap.put("status", "offline");
//                        hashMap.put("search", username.toLowerCase());

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    //change Login to HomeActivity
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);//MainActivity.class.I think this is after the confirm signUp
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(SignUpActivity.this, "You can't register with this email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
