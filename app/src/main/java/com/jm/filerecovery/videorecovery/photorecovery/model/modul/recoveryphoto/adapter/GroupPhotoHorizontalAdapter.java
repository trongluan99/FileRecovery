package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.AlbumPhoto;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model.PhotoEntity;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;

public class GroupPhotoHorizontalAdapter extends RecyclerView.Adapter<GroupPhotoHorizontalAdapter.MyViewHolder> {
    Context context;
    ArrayList<AlbumPhoto> al_menu = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;

    public GroupPhotoHorizontalAdapter(Context context, ArrayList<AlbumPhoto> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.al_menu = mList;
        mOnClickItemListener = onClickItemListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected RecyclerView recycler_view_list;
        protected TextView albumName;

        OnClickItemListener onClickItemListener;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);
            this.recycler_view_list = view.findViewById(R.id.recycler_view_list);
            this.albumName = view.findViewById(R.id.tv_album_name);
            this.onClickItemListener = onClickItemListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickItemListener.onClickItem(getAdapterPosition());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_album_2, parent, false);
        return new MyViewHolder(itemView, mOnClickItemListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ArrayList<PhotoEntity> singleSectionItems = al_menu.get(position).getListPhoto();
        FilePhotoGridAdapter itemListDataAdapter = new FilePhotoGridAdapter(context, singleSectionItems, position);
        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setAdapter(itemListDataAdapter);

        if (!TextUtils.isEmpty(al_menu.get(position).getStr_folder())) {
            String title = FilenameUtils.getBaseName(al_menu.get(position).getStr_folder());
            if (TextUtils.isEmpty(title)) {
                title = FilenameUtils.getName(al_menu.get(position).getStr_folder());
            }
            if (TextUtils.isEmpty(title)) {
                title = FilenameUtils.getExtension(al_menu.get(position).getStr_folder());
            }
            holder.albumName.setText(title);
        }
    }

    @Override
    public int getItemCount() {
        return al_menu.size();
    }

    public interface OnClickItemListener {
        void onClickItem(int position);


    }


}
