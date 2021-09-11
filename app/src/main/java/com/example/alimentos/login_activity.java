package com.example.alimentos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;
import java.util.Locale;

public class login_activity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextEmail,editTextPassword;

    public static String cityyy;
    private FirebaseAuth mAuth;

    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;

    FirebaseAuth firebaseAuth;

//import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        btSignIn = findViewById(R.id.go_sign_in);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN

        ).requestIdToken("501875058100-jou4ob6cgs83s1as30bqs239hi3i65ui.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(login_activity.this,googleSignInOptions);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,100);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

  /*      if (firebaseUser!=null){
            startActivity(new Intent(login_activity.this,activity_nav.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

*/





        Button signIn = findViewById(R.id.button_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        editTextEmail = findViewById(R.id.email_login);
        editTextPassword = findViewById(R.id.pass_login);

        mAuth  = FirebaseAuth.getInstance();


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //  locationEnabled();
        getLocation();




    }

    private void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5,this::onLocationChanged);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        else if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        else if (password.length()<6){
            editTextPassword.setError("Min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;

        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login_activity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(login_activity.this,activity_nav.class));

                }else {
                    Toast.makeText(login_activity.this,"Failed to login! Please " +
                            "Check your credentials ",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public String givecity(){
        return cityyy;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if (signInAccountTask.isSuccessful()){
                String s = "Google sign in successful";

                displayToast(s);

                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);

                    if (googleSignInAccount != null){
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken(),null);

                            firebaseAuth.signInWithCredential(authCredential)
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(login_activity.this,activity_nav
                                                .class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                                displayToast("Firebase authentication succesfful");

                                            }else{
                                                displayToast("Authentication Failed : "+task.getException().getMessage());
                                            }
                                        }
                                    });


                    }

                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    private void displayToast(String s) {

        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            cityyy = addresses.get(0).getLocality().replaceAll("\\s+", "");

       //     Toast.makeText(getApplicationContext(),"This is city name : "+cityyy,Toast.LENGTH_LONG).show();



        } catch (Exception e) {
        }

    }



}

