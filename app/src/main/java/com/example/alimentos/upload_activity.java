package com.example.alimentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

public class upload_activity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage ;
    private Button mButtonUpload;
    private Button mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private String fedit;
    private String cityyy;


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null
                && data.getData()!=null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_activity);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.button_to_show_uploads);
        mEditTextFileName = findViewById(R.id.text_view_file_name);
        mImageView = findViewById(R.id.image_view);
      //  mProgressBar = findViewById(R.id.progress_bar);

        fedit = mEditTextFileName.getText().toString().trim();

        /*  mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });  */

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously();


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      //  locationEnabled();
        getLocation();




        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");





        mButtonChooseImage.setOnClickListener((view) ->{
            Intent intent_4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(intent_4, 0);

        });

       mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

       mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openImagesActivity();
           }
       });






    }

    private void getLocation() {


            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,this::onLocationChanged);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

     //   if(requestCode==1 && resultCode==RESULT_OK){

            Bitmap bitmap_2 = (Bitmap)data.getExtras().get("data");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap_2.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap_2,"val",null);
          //  Uri uri = Uri.parse(path);

            mImageUri = Uri.parse(path);
            mImageView.setImageURI(mImageUri);


           // mImageUri = data.getData();


            //mImageView.setImageBitmap(bitmap_2);

      //  }


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public String givecity(){
        return cityyy;
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    private void uploadFile(){
        if(mImageUri !=null){
            StorageReference fileReference = mStorageRef.child((mEditTextFileName.getText().toString()+"."+getFileExtension(mImageUri)));
            fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task <Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();



                        upload upload = new upload(mEditTextFileName.getText().toString(),downloadUri.toString(),cityyy.trim(),user.getEmail());
                        mDatabaseRef.push().setValue(upload);

                        Toast.makeText(upload_activity.this,"Upload Sucessfull",Toast.LENGTH_SHORT).show();



                    }else {

                    }
                }
            }) .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload_activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

                    /*fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                         //   upload upload = new upload(mEditTextFileName.toString().trim(),);
                            String modelId = fileReference.
                        }
                    });  */





    }

}

private void openImagesActivity(){
    Intent intent = new Intent(upload_activity.this,ImagesActivity.class) ;
    startActivity(intent);

}


    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

           cityyy = addresses.get(0).getLocality().replaceAll("\\s+", "");

      //      Toast.makeText(getApplicationContext(),"This is city name : "+cityyy,Toast.LENGTH_LONG).show();



        } catch (Exception e) {
        }

    }

}