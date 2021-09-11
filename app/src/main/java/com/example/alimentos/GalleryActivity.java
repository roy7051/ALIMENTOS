package com.example.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryActivity extends AppCompatActivity {

    ImageView img;
    TextView tv1;
    Button btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        img = findViewById(R.id.image_gallery);
        tv1 = findViewById(R.id.image_description);
        btn_order = findViewById(R.id.button_order);

        img.setImageResource(getIntent().getIntExtra("image",0));
        tv1.setText(getIntent().getStringExtra("image_text"));

        String nameofitem = getIntent().getStringExtra("image_text");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GalleryActivity.this,"Your item will be reaching you soon.",Toast.LENGTH_LONG).show();

                FirebaseDatabase.getInstance()
                        .getReference("orders")
                        .push()
                        .setValue(new orders(nameofitem,user.getEmail()));
            }
        });









    }



}