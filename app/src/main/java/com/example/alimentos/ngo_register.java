package com.example.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ngo_register extends AppCompatActivity {

    private EditText inputEmail, inputPassword;

    public Button btn_1;
    EditText name;
    EditText pass;
    EditText email;
    EditText address;
    String Name, Password, Email;
    EditText password;

    DatabaseReference mdatabase;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_register);

        btn_1 = (Button) findViewById(R.id.button6);
        name = (EditText) findViewById(R.id.ngo_name_edit_text);
        password = (EditText) findViewById(R.id.ngo_pass);
        email = (EditText) findViewById(R.id.ngo_email);
        address = (EditText) findViewById(R.id.ngo_city);

        mAuth = FirebaseAuth.getInstance();

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });


    }

    private void registerUser() {
        String new_email = email.getText().toString().trim();
        String new_pass = password.getText().toString().trim();
        String new_name = name.getText().toString().trim();
        String new_add = address.getText().toString().trim();

        if (new_name.isEmpty()) {
            name.setError("Ngo name is required");
            name.requestFocus();
            return;
        }

        else if (new_email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(new_email).matches()) {
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        else if (new_pass.length() < 6) {
            password.setError("Min password length should be 6 characters");
            password.requestFocus();
            return;

        }


        else if (new_add.isEmpty()) {
            address.setError("Location is required");
            address.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(new_email,new_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Ngo user = new Ngo(new_name,new_email,new_add);

                    FirebaseDatabase.getInstance().getReference("ngo").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ngo_register.this,"Ngo has been registered succesfully",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(ngo_register.this,login_activity.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(ngo_register.this,"Failed to Register ! Try Again !",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(ngo_register.this,"Failed to Register ",Toast.LENGTH_LONG).show();

                }
            }
        });


    }

}