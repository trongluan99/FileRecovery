package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.PhotosActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class FilePhotoGridAdapter extends RecyclerView.Adapter<FilePhotoGridAdapter.SingleItemRowHolder> {

    private ArrayList<PhotoEntity> itemsList;
    private Context mContext;
    int size;
    int postion;

    public FilePhotoGridAdapter(Context context, ArrayList<PhotoEntity> itemsList, int mPostion) {
        this.itemsList = itemsList;
        this.mContext = context;
        postion = mPostion;
        if (itemsList.size() >= 4) {
            size = 4;
        } else {
            size = itemsList.size();
        }
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        PhotoEntity singleItem = itemsList.get(i);

        try {
            Glide.with(mContext)
                    .load("file://" + singleItem.getPathPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .into(holder.itemImage);
        } catch (Exception e) {
            //do nothing
            Toast.makeText(mContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhotosActivity.class);
                intent.putExtra("value", postion);
                mContext.startActivity(intent);
            }
        });

        if (itemsList.size() > 4 && i == 3) {
            holder.tvCount.setVisibility(View.VISIBLE);
            holder.ivMask.setVisibility(View.VISIBLE);
            holder.tvCount.setText((itemsList.size() - 4) + "+");
        } else {
            holder.tvCount.setVisibility(View.GONE);
            holder.ivMask.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {


        protected RoundedImageView itemImage;
        protected RoundedImageView ivMask;
        protected AppCompatTextView tvCount;


        public SingleItemRowHolder(View view) {
            super(view);

            this.itemImage = view.findViewById(R.id.ivImage);
            this.ivMask = view.findViewById(R.id.iv_mask);
            this.tvCount = view.findViewById(R.id.tv_count);


        }

    }

}