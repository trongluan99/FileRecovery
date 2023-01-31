package com.jm.filerecovery.videorecovery.photorecovery.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import java.util.ArrayList;

public class ItemPhotoSelectAdapter extends RecyclerView.Adapter<ItemPhotoSelectAdapter.MyViewHolder>{
    Context context;
    ArrayList<PhotoEntity> photoEntities = new ArrayList<>();
    BitmapDrawable placeholder;
    public ItemPhotoSelectAdapter(Context context, ArrayList<PhotoEntity> mList) {
        this.context = context;
        this.photoEntities = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView mediaThumb;
        public MyViewHolder(View view) {
            super(view);
            mediaThumb = view.findViewById(R.id.mediaThumb);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_choosed, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PhotoEntity photoEntity = photoEntities.get(position);
        Log.d("Duongdx", "Size: "+photoEntities.size()+"");
        try {
            Glide.with(context)
                    .load("file://" + photoEntity.getPathPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .placeholder(placeholder)
                    .into(holder.mediaThumb);
        } catch (Exception e){
            //do nothing
            Toast.makeText(context, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public int getItemCount() {
        Log.d("Duongdx", "Size: "+photoEntities.size()+"");
        return photoEntities.size();
    }

    public void setPhotoEntities(ArrayList<PhotoEntity> photoEntities) {
        this.photoEntities = photoEntities;
    }

    public void setAllImagesUnSelected() {
        if (this.photoEntities != null) {
            for (int i = 0; i < this.photoEntities.size(); i++) {
                if ((this.photoEntities.get(i)).getIsCheck()) {
                    (this.photoEntities.get(i)).setIsCheck(false);
                }
            }
        }
    }
}

