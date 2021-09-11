package com.example.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signup_second_layout extends AppCompatActivity {

    Button ngo_btn;
    Button single_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_second_layout);

        ngo_btn = findViewById(R.id.button_ngo);
        single_btn = findViewById(R.id.button_single_user);


        ngo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup_second_layout.this,ngo_register.class);
                startActivity(intent);
            }
        });

        single_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup_second_layout.this,MainActivity2.class);
                startActivity(intent);
            }
        });



    }
}