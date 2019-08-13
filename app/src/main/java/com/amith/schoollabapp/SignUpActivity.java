package com.amith.schoollabapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText nameWithInitial, userName, email, password, cpassword, phoneNumber;
    Button btnSignUp;
    RadioGroup radioGroupGender;
    AppCompatRadioButton radioButtonGender;
    //RadioButton radioButtonGender;

    String strGender="Male", strMale="Male", strFemale="Female";

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP));
            }
        });

        nameWithInitial = findViewById(R.id.signup_nameWithInitial);
        userName = findViewById(R.id.signup_userName);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        cpassword = findViewById(R.id.signup_confirmPassword);
        phoneNumber = findViewById(R.id.signup_phoneNumber);
        btnSignUp = findViewById(R.id.signup_btnSignUp);  //SignUp Button

        auth = FirebaseAuth.getInstance();

//        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                radioButtonGender = radioGroupGender.findViewById(checkedId);
//
//                switch (checkedId) {
//                    case R.id.radio_male:
//                        strGender = radioButtonGender.getText().toString();
//                        //strGender = strMale;
//                        break;
//                    case R.id.radio_female:
//                        strGender = radioButtonGender.getText().toString();
//                        //strGender = strFemale;
//                        break;
//                    default:
//                        strGender = null;
//                }
//
//            }
//        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioGroupGender != null) {
                    int radioId = radioGroupGender.getCheckedRadioButtonId();
                    radioButtonGender = findViewById(radioId);

//                    if (radioId == R.id.radio_male) {
//                        //strGender = radioButtonGender.getText().toString();
//                        strGender = strMale;
//                        Log.e("Gender in radio", "1");
//                        Toast.makeText(SignUpActivity.this, "Gender = 1", Toast.LENGTH_SHORT).show();
//                    } else {
//                        //strGender = radioButtonGender.getText().toString();
//                        strGender = strFemale;
//                        Toast.makeText(SignUpActivity.this, "Gender = 2", Toast.LENGTH_SHORT).show();
//                        Log.e("Gender in radio", "2");
//                    }
                }

                Log.v("Gender in radio", strGender);
                Toast.makeText(SignUpActivity.this, "Gender = "+strGender, Toast.LENGTH_LONG).show();

                String txt_nameWithInitial = nameWithInitial.getText().toString();
                String txt_username = userName.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_cpassword = cpassword.getText().toString();
                String txt_phoneNumber = phoneNumber.getText().toString();
                String txt_gender = strGender;

                if (TextUtils.isEmpty(txt_nameWithInitial) || TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) ||
                        TextUtils.isEmpty(txt_phoneNumber) || TextUtils.isEmpty(txt_gender) || TextUtils.isEmpty(txt_password) ||
                        TextUtils.isEmpty(txt_cpassword)){
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length()<8){
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else if (!(txt_cpassword.contentEquals(txt_password))){
                    Toast.makeText(SignUpActivity.this, "Password and Confirm Password are not match", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_nameWithInitial, txt_username, txt_email, txt_phoneNumber, txt_gender, txt_password);
                }
            }
        });
    }

    public void checkButton(View v){

        if (radioGroupGender != null) {
            int radioId = radioGroupGender.getCheckedRadioButtonId();
            radioButtonGender = findViewById(radioId);

            if (radioId == R.id.radio_male) {
                strGender = radioButtonGender.getText().toString();
                //strGender = strMale;
                Log.e("Gender in radio", "1");
                Toast.makeText(SignUpActivity.this, "Gender = 1", Toast.LENGTH_SHORT).show();
            } else {
                strGender = radioButtonGender.getText().toString();
                //strGender = strFemale;
                Toast.makeText(SignUpActivity.this, "Gender = 2", Toast.LENGTH_SHORT).show();
                Log.e("Gender in radio", "2");
            }
        }
    }

    private void register(final String nameWithInitial, final String username, final String email, final String phoneNumber, final String gender, String password){ // String phoneNumber not dded
        auth.createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();

                        firebaseUser.updateProfile(profileUpdates);
                        String userId = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", userId);
                        hashMap.put("nameWithInitial", nameWithInitial);
                        hashMap.put("username", username);
                        hashMap.put("imageURL", "default");
                        hashMap.put("email", email);
                        hashMap.put("phoneNumber", phoneNumber);
                        hashMap.put("gender", gender);
                        hashMap.put("approve", "no");
//                        hashMap.put("status", "offline");
//                        hashMap.put("search", username.toLowerCase());

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();
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
