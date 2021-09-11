package com.example.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //public Button button;
    private static int SPLASH_TIME_OUT  = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView bl_text;
        Animation blink_anim;


        bl_text = findViewById(R.id.blinker);

        blink_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        bl_text.startAnimation(blink_anim);



       /* button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,upload_activity.class);
                startActivity(intent);
            }
        });  */

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            },SPLASH_TIME_OUT);



    }
}