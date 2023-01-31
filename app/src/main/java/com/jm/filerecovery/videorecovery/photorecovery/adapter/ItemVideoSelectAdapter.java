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
import com.jm.filerecovery.videorecovery.photorecovery.model.SquareImageView;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;

import java.util.ArrayList;

public class ItemVideoSelectAdapter extends RecyclerView.Adapter<ItemVideoSelectAdapter.MyViewHolder>{
    Context context;
    ArrayList<VideoEntity> videoEntities = new ArrayList<>();
    BitmapDrawable placeholder;
    public ItemVideoSelectAdapter(Context context, ArrayList<VideoEntity> mList) {
        this.context = context;
        this.videoEntities = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView mediaThumb;
        SquareImageView imgPlay;
        public MyViewHolder(View view) {
            super(view);
            mediaThumb = view.findViewById(R.id.mediaThumb);
            imgPlay = view.findViewById(R.id.img_play);
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
        final VideoEntity videoEntity = videoEntities.get(position);
        try {
            holder.imgPlay.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load("file://" + videoEntity.getPathPhoto())
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
        return videoEntities.size();
    }

    public void setVideoEntities(ArrayList<VideoEntity> videos) {
        this.videoEntities = videos;
    }

}

