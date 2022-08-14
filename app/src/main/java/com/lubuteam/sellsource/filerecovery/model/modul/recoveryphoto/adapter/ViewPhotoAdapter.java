package com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.model.SquareImageView;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.lubuteam.sellsource.filerecovery.utilts.Utils;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;

public class ViewPhotoAdapter extends RecyclerView.Adapter<ViewPhotoAdapter.MyViewHolder>{
    Context context;
    ArrayList<PhotoModel> listPhoto = new ArrayList<>();
    BitmapDrawable placeholder;
    public ViewPhotoAdapter(Context context, ArrayList<PhotoModel> mList) {
        this.context = context;
        this.listPhoto = mList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvSize;
        ImageView ivPhoto;
        CardView album_card;
        public MyViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            ivPhoto = (SquareImageView) view.findViewById(R.id.iv_image);
            album_card = (CardView)view.findViewById(R.id.album_card);
        }
    }
    @Override
    public ViewPhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_photo, parent, false);
        return new ViewPhotoAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewPhotoAdapter.MyViewHolder holder, int position) {
        final PhotoModel imageData = listPhoto.get(position);
        holder.tvDate.setText(DateFormat.getDateInstance().format(imageData.getLastModified()));
        holder.tvSize.setText(Utils.formatSize(imageData.getSizePhoto()));

        try {
            Glide.with(context)
                    .load("file://" + imageData.getPathPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .placeholder(placeholder)
                    .into(holder.ivPhoto);
        } catch (Exception e){
            //do nothing
            Toast.makeText(context, "Exception: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                }catch (Exception e){

                }
                Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(imageData.getPathPhoto()));
                Intent openPhotoIntent = new Intent();
                openPhotoIntent.setAction(Intent.ACTION_VIEW)
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        .setDataAndType(fileUri, context.getContentResolver().getType(fileUri));
                context.startActivity(openPhotoIntent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return listPhoto.size();
    }

}
