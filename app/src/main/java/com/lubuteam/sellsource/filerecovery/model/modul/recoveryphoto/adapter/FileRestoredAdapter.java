package com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.databinding.ItemFileRestoredBinding;
import com.lubuteam.sellsource.filerecovery.databinding.ItemPhotoRestoredBinding;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class FileRestoredAdapter extends RecyclerView.Adapter<FileRestoredAdapter.ViewHolder> {

    public File[] lstData;
    private int typeItem = 0;
    private ItemClickListener itemClickListener;

    public FileRestoredAdapter(File[] lstData, int typeItem, ItemClickListener itemClickListener) {
        this.lstData = lstData;
        this.typeItem = typeItem;
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickListener(File file);
    }

    @NonNull
    @Override
    public FileRestoredAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_restored, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileRestoredAdapter.ViewHolder holder, int position) {
        holder.bindData(lstData[position]);
    }

    @Override
    public int getItemCount() {
        return lstData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFileRestoredBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemFileRestoredBinding.bind(itemView);
        }

        public void bindData(File file) {
            binding.tvFileName.setVisibility(View.GONE);
            binding.ivAudio.setVisibility(View.GONE);
            if (typeItem == 0) {
                /*image*/
                binding.ivMask.setVisibility(View.GONE);
                Glide.with(itemView.getContext())
                        .load(file.getPath())
                        .into(binding.ivPhoto);
            } else if (typeItem == 1) {
                /*audio*/
                binding.ivMask.setVisibility(View.GONE);
                Glide.with(itemView.getContext())
                        .load(R.drawable.bg_item_audio)
                        .into(binding.ivPhoto);
                binding.tvFileName.setVisibility(View.VISIBLE);
                binding.ivAudio.setVisibility(View.VISIBLE);
                binding.tvFileName.setText(FilenameUtils.getName(file.getPath()));
            } else if (typeItem == 2) {
                /*video*/
                binding.ivMask.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                        .load(file.getPath())
                        .into(binding.ivPhoto);
            }

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClickListener(file);
                }
            });
        }
    }

}
