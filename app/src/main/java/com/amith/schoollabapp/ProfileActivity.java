package com.amith.schoollabapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amith.schoollabapp.Model.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.security.Key;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    //views from xml
    CircleImageView image_profile;
    TextView username, userId, userNameWithInitial, userEmail, userPhoneNumber, userGender;
    FloatingActionButton fab;

    //progress dialog
    ProgressDialog pd;

    //storage
    DatabaseReference reference, referenceChild;

    //storage path
    String storagePath = "Users_Profile_Cover_Imgs";

    FirebaseUser fuser;
    FirebaseAuth auth;

    //permissions constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;
    //arrays of permissions to be requested
    String cameraPermissions[];
    String storagePermissions[];

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    //for checking profile or cover photo
    String profileOrCoverPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, NavigationActivity.class));
            }
        });
//
        auth = FirebaseAuth.getInstance();
        image_profile = findViewById(R.id.profile_image);
        image_profile.setImageResource(R.drawable.pp_icon);
        username = findViewById(R.id.username);
        //userId = findViewById(R.id.user_id);
        userNameWithInitial = findViewById(R.id.user_nameWithInitial);
        userEmail = findViewById(R.id.user_email);
        userPhoneNumber = findViewById(R.id.user_phone_no);
        userGender = findViewById(R.id.user_gender);
        fab = findViewById(R.id.fab);

        //init progress dialog
        pd = new ProgressDialog(ProfileActivity.this);

        //firebase connections
        fuser =auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        //init arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("Profile Activity","Called onDataChange");
               // Toast.makeText(ProfileActivity.this, "pp", Toast.LENGTH_SHORT).show();

                try{
                    User user = dataSnapshot.getValue(User.class);
                    username.setText(user.getUsername());
                    //userId.setText(user.getId());
                    userNameWithInitial.setText(user.getNameWithInitial());
                    userEmail.setText(user.getEmail());
                    userPhoneNumber.setText(user.getPhoneNumber());
                    userGender.setText(user.getGender());

                    if(user.getImageURL().equals("default")){
                        Log.v("Profile Activity","Came to image url default");
                        image_profile.setImageResource(R.drawable.pp_icon);
                    } else {
                        Glide.with(ProfileActivity.this).load(user.getImageURL()).into(image_profile);
                    }
                } catch (Exception e) {
                    //Toast.makeText(ProfileActivity.this, "pp & user: "+e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.v("PP & user", e.getMessage());
                }
                //Toast.makeText(ProfileActivity.this, "pp", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Floating Action Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
        
    }

    private boolean checkStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result;
    }
    private void requestStoragePermission() {
        //request runtime storage permission
        ActivityCompat.requestPermissions(ProfileActivity.this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){

        boolean result = ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }
    private void requestCameraPermission() {
        //request runtime storage permission
        //because "requestPermissions(cameraPermissions, CAMERA_REQUEST_CODE);" not supported for API lvl 19
        ActivityCompat.requestPermissions(ProfileActivity.this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private void showEditProfileDialog() {

        String options[] = {"Change Profile Picture", "Change username", "Change name with initials", "Change Contact no", "Change gender"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        //silva's code
//        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//        alertDialog.setTitle("Alert");
//        alertDialog.setMessage("Alert message to be shown");
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();

        builder.setTitle("Choose Action");
        //=set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item clicks
                if (which == 0) {
                    //change profile picture clicked
                    pd.setMessage("Updating profile picture");
                    profileOrCoverPhoto = "image";
                    showImagePicDialog();
                } else if (which == 1) {
                    //change username clicked
                    pd.setMessage("Updating username");
                    //calling method and pass key "name" as parameter to update it's value in database
                    showUsernameDialog("username");
                } else if (which == 2) {
                    //change name with initial clicked
                    pd.setMessage("Updating name with initials");
                    showNameWithInitialDialog("nameWithInitial");
                } else if (which == 3) {
                    //change contact number clicked
                    pd.setMessage("Updating contact No");
                    showPhoneNoDialog("phoneNumber");
                } else if (which == 4) {
                    //change gender clicked
                    pd.setMessage("Updating gender");
                    showGenderDialog("gender");
                }
            }
        });
        //create and show dialog
        builder.create().show();

    }

    //change user text details
    private void showUsernameDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + key); // e.g. Update username

        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10, 10, 10);

        //add edit text
        final MaterialEditText materialEditText = new MaterialEditText(this);
        materialEditText.setHint("Enter "+key); //e.g. Enter username
        linearLayout.addView(materialEditText);

        builder.setView(linearLayout);

        //add buttons in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from material edit text
                String value = materialEditText.getText().toString().trim();

                //validate if user entered something or not
                if (!TextUtils.isEmpty(value)) {
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    reference.updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //update, dismiss progress
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //failed, dismiss progress, get and show error message
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(ProfileActivity.this, "Please enter "+key, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add buttons in dialog to cancel
        builder.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showNameWithInitialDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + key); // e.g. Update name

        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10, 10, 10);

        //add edit text
        final MaterialEditText materialEditText = new MaterialEditText(this);
        materialEditText.setHint("Enter name with initial");
        linearLayout.addView(materialEditText);

        builder.setView(linearLayout);

        //add buttons in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from material edit text
                String value = materialEditText.getText().toString().trim();

                //validate if user entered something or not
                if (!TextUtils.isEmpty(value)) {
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    reference.updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //update, dismiss progress
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //failed, dismiss progress, get and show error message
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(ProfileActivity.this, "Please enter "+key, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add buttons in dialog to cancel
        builder.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showPhoneNoDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + key); // e.g. Update username

        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10, 10, 10);

        //add edit text
        final MaterialEditText materialEditText = new MaterialEditText(this);
        materialEditText.setHint("Enter Contact No");
        linearLayout.addView(materialEditText);

        builder.setView(linearLayout);

        //add buttons in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from material edit text
                String value = materialEditText.getText().toString().trim();

                //validate if user entered something or not
                if (!TextUtils.isEmpty(value)) {
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    reference.updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //update, dismiss progress
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //failed, dismiss progress, get and show error message
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(ProfileActivity.this, "Please enter "+key, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add buttons in dialog to cancel
        builder.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showGenderDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update " + key); // e.g. Update username

        //set layout of dialog
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10, 10, 10);

        //add edit text
        final MaterialEditText materialEditText = new MaterialEditText(this);
        materialEditText.setHint("Enter Gender(Male/Female)");
        linearLayout.addView(materialEditText);

        builder.setView(linearLayout);

        //add buttons in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from material edit text
                String value = materialEditText.getText().toString().trim();

                //validate if user entered something or not
                if (!TextUtils.isEmpty(value)) {
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    reference.updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //update, dismiss progress
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //failed, dismiss progress, get and show error message
                                    pd.dismiss();
                                    Toast.makeText(ProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(ProfileActivity.this, "Please enter "+key, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //add buttons in dialog to cancel
        builder.setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showImagePicDialog() {
        //show dialog containing optioning options Camera and Gallery to pick the image

        String options[] = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        builder.setTitle("Pick Image From");
        //=set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item clicks
                if (which == 0) {
                    //camera clicked
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }

                } else if (which == 1) {
                    //gallery clicked
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        //create and show dialog
        builder.create().show();

        //for picking image from
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                //picking from camera, first check if camera & storage permissions allowed or not
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        //permission enabled
                        pickFromCamera();
                    } else {
                        //permission denied
                        Toast.makeText(this, "Please enable camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                //picking from gallery, first check if storage permissions allowed or not
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        //permission enabled
                        pickFromGallery();
                    } else {
                        //permission denied
                        Toast.makeText(this, "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    //problem onActivityReenter
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode ==RESULT_OK) {
//            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
//                //image is picked from gallery, get uri of image
//                imageUri = data.getData();
//
//                //uploadProfileCoverPhoto(imageUri) function
//                uploadImage();
//            }
//            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
//                //image is picked from gallery, get uri of image
//
//                //uploadProfileCoverPhoto(imageUri) function
//                uploadImage();
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void uploadProfileCoverPhoto(Uri imageUri) {
        pd.show();
        String filePathAndName = storagePath +""+ profileOrCoverPhoto +""+ fuser.getUid();

        // continue...................
    }

    private void pickFromCamera() {
        //intent of picking image from device camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //put image uri
        imageUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {
        //pick from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading"); //loading text while profile picture uploading
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        //reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getParent(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getParent(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getParent(), "No image selected", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getParent(), "Upload in progress", Toast.LENGTH_SHORT).show();//33333
            } else {
                uploadImage();
            }
        }
    }
}
