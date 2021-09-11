package com.example.alimentos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
        private Context mContext;
        private List<upload> mUploads;

        public ImageAdapter(Context context,List<upload> uploads){
            mContext =context;
            mUploads = uploads;
        }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ImageViewHolder holder, int position) {
        upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(mContext).load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop()
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
