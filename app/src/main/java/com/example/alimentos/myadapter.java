package com.example.alimentos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimentos.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    public  ArrayList<datamodel> dataholder;
    public Context myContext;
    public String foodid;
    public static String  text_for_food_id;


    public myadapter(){

    }




    public myadapter(Context context , ArrayList<datamodel> dataholder) {
        this.dataholder = dataholder;
        myContext = context;


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

    //     foodid = dataholder.get(position).getImageUrl();


      //    kpos = position;



  //      food_item fooditem = new food_item();

//        food_item foodcont = new food_item(myContext,dataholder,position);






        Picasso.with(myContext).load(dataholder.get(position).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop()
                .into(holder.img);

        holder.header.setText(dataholder.get(position).getName());





        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text_for_food_id = dataholder.get(position).getName();

                Intent intent = new Intent(myContext,food_item.class);
                intent.putExtra("image_text_food",text_for_food_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                myContext.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
        {
            ImageView img;
            TextView header;

            public myviewholder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.image_single);
                header = itemView.findViewById(R.id.image_single_title);


            }
        }

}
