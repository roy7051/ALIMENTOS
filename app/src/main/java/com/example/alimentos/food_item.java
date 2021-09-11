package com.example.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class food_item extends AppCompatActivity {

    ImageView img_food;
    TextView tv_food;
    Button btn_food_btn;
    Button btn_chat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

     //   img_food = findViewById(R.id.image_food_item);
        tv_food = findViewById(R.id.image_desc_food);
        btn_food_btn = findViewById(R.id.button_food_order);

        tv_food.setText(getIntent().getStringExtra("image_text_food"));

        String itname = getIntent().getStringExtra("image_text_food");
        //    btn_chat = findViewById(R.id.button_chat);



    //   myadapter food_adapter = new myadapter();

   /*    Picasso.with(getApplicationContext()).load(getIntent().getIntExtra("image_food",0))
               .placeholder(R.mipmap.ic_launcher)
               .fit().centerCrop()
               .into(img_food); */

    /*    Picasso.with(mycontext).load(dataholder.get(position).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop()
                .into(img_food);


*/







      //  img_food.setImageResource(getIntent().getIntExtra("image_food",0));



            myadapter ad = new myadapter();

//        Toast.makeText(this,"The string is "+getIntent().getIntExtra("image_food",0),Toast.LENGTH_LONG).show();

      // Toast.makeText(this,"The string of dataholder is "+ad.text_for_food_id,Toast.LENGTH_LONG).show();

   /*     btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(food_item.this,chatmain.class);
                startActivity(intent);
            }
        });   */

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        btn_food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(food_item.this,"Your item will be reaching to you very soon",Toast.LENGTH_SHORT).show();

                FirebaseDatabase.getInstance()
                        .getReference("orders")
                        .push()
                        .setValue(new orders(itname,user.getEmail()));

            }
        });









    }
}