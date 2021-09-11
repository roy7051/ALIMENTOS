package com.example.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class category extends AppCompatActivity {

    Button btn_5,btn_6;
    static String role_receiver = "receiver";
    static String role_donor = "donor";


    DatabaseReference mdatabase;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btn_5 = findViewById(R.id.button7);
        btn_6 = findViewById(R.id.button8);

        mAuth = FirebaseAuth.getInstance();



        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //register_receiver_details

                MainActivity2 sign_up_user = new MainActivity2();

                Roleplay role_player = new Roleplay(sign_up_user.new_name,sign_up_user.new_email,role_receiver);

                FirebaseDatabase.getInstance().getReference("receiver").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).setValue(role_player).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(category.this,"Your role has been saved",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(category.this,login_activity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(category.this,"Failed to Register ! Try Again !",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                

            }


        });




        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //register_receiver_details

                MainActivity2 sign_up_user = new MainActivity2();

                Roleplay role_player = new Roleplay(sign_up_user.new_name,sign_up_user.new_email,role_donor);

                FirebaseDatabase.getInstance().getReference("donor").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).setValue(role_player).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(category.this,"Your role has been saved",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(category.this,login_activity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(category.this,"Failed to Register ! Try Again !",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }


        });







    }
}