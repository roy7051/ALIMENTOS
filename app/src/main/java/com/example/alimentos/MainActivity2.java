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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

//import java.sql.Date;
//This activity is for sign up user
public class MainActivity2 extends AppCompatActivity {

    private EditText inputEmail, inputPassword;

    public Button btn_1;
    EditText name;
    EditText pass;
    EditText email;
    String Name, Password, Email;
    EditText password;
    static String new_email;
    static String new_pass;
    static String new_name;

    DatabaseReference mdatabase;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_1 = (Button) findViewById(R.id.button5);
        name = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText);

        

        mAuth = FirebaseAuth.getInstance();


        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });


    }

    private void registerUser() {
         new_email  = email.getText().toString().trim();
        new_pass = password.getText().toString().trim();
        new_name = name.getText().toString().trim();

        if (new_name.isEmpty()){
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }

        if (new_email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(new_email).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if (new_pass.length()<6){
            password.setError("Min password length should be 6 characters");
            password.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(new_email,new_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(new_name,new_email);

                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity2.this,"User has been registered succesfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity2.this,category.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(MainActivity2.this,"Failed to Register ! Try Again !",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(MainActivity2.this,"Failed to Register ",Toast.LENGTH_LONG).show();

                }
            }
        });


    }

}
