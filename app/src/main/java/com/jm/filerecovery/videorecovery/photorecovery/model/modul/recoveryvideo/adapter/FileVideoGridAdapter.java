package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.SquareImageView;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.VideoActivity;

import java.util.ArrayList;

public class FileVideoGridAdapter extends RecyclerView.Adapter<FileVideoGridAdapter.SingleItemRowHolder> {

    private ArrayList<VideoEntity> itemsList;
    private Context mContext;
    int size;
    int postion;
    public FileVideoGridAdapter(Context context, ArrayList<VideoEntity> itemsList, int mPostion) {
        this.itemsList = itemsList;
        this.mContext = context;
        postion = mPostion;
        if(itemsList.size()>=5){
            size=5;
        }else{
            size =itemsList.size();
        }
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.
                item_image_video, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        VideoEntity singleItem = itemsList.get(i);

        try {
            Glide.with(mContext)
                    .load("file://" + singleItem.getPathPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(holder.itemImage);
        } catch (Exception e){
            //do nothing
            Toast.makeText(mContext, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("value",postion);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {



        protected SquareImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.itemImage = (SquareImageView) view.findViewById(R.id.ivImage);



        }

    }

}