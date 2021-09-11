package com.example.alimentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class recycle_activity extends AppCompatActivity {

    private RecyclerView recyclerView_1;
    private ProgrammingAdapter adapter;

    private int[] images = {R.drawable.bandages_img,R.drawable.painkiller,R.drawable.coldpack,R.drawable.dettol,R.drawable.benadryl,R.drawable.antiseptic_image,R.drawable.thermo_image,R.drawable.pocket_mask,
            R.drawable.decongestant,R.drawable.aspirin_image};
    private String[] text_for_images = {"Bandages","Painkiller","Cold Pack","Dettol","Benadryl","Antiseptic","Thermometer","Pocket Mask","Oral decongestant","Aspirin"};


    private  RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_activity);


        recyclerView_1 = findViewById(R.id.programmingList);
        //   layoutManager = new GridLayoutManager(this,1);
        //layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);

        //recyclerView_1.setHasFixedSize(true);
        //recyclerView_1.setLayoutManager(layoutManager);
        recyclerView_1.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProgrammingAdapter(images,text_for_images,getApplicationContext());
        recyclerView_1.setAdapter(adapter);


    }
}