package com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.databinding.ItemPhotoRestoredBinding;

import java.io.File;

public class PhotoRestoredAdapter extends RecyclerView.Adapter<PhotoRestoredAdapter.ViewHolder> {

    public File[] lstData;
    private ItemClickListener itemClickListener;

    public PhotoRestoredAdapter(File[] lstData, ItemClickListener itemClickListener) {
        this.lstData = lstData;
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(File file);
    }

    @NonNull
    @Override
    public PhotoRestoredAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_restored, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoRestoredAdapter.ViewHolder holder, int position) {
        holder.bindData(lstData[position]);
    }

    @Override
    public int getItemCount() {
        return lstData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemPhotoRestoredBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPhotoRestoredBinding.bind(itemView);
        }

        public void bindData(File file) {
            Glide.with(itemView.getContext())
                    .load(file.getPath())
                    .into(binding.ivPhoto);

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClickListener(file);
                }
            });
        }
    }

}
