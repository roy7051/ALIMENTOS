package com.example.alimentos;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.ImageViewHolder>{

    private int[] images;
    private String[] text_for_images;
    Context mContext;



    public ProgrammingAdapter(int[] images,String[] text_for_images,Context mContext){
        this.images = images;
        this.text_for_images = text_for_images;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int image_id = images[position] ;
        String text_for_images_id = text_for_images[position];
        holder.album.setImageResource(image_id);
        //   holder.albumtitle.setText("Image : "+position);
        holder.albumtitle.setText(text_for_images_id);


        holder.albumtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,GalleryActivity.class);
                intent.putExtra("image",image_id);
                intent.putExtra("image_text",text_for_images_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                mContext.startActivity(intent);
            }
        });




    }


    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView album;
        TextView albumtitle;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            album = itemView.findViewById(R.id.album);
            albumtitle = itemView.findViewById(R.id.alb_title);
        }
    }
}