package com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.Model.AlbumPhoto;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;

import java.util.ArrayList;

public class AlbumsPhotoAdapter extends RecyclerView.Adapter<AlbumsPhotoAdapter.MyViewHolder>{
    Context context;
    ArrayList<AlbumPhoto> al_menu = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;
    public AlbumsPhotoAdapter(Context context, ArrayList<AlbumPhoto> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.al_menu = mList;
        mOnClickItemListener = onClickItemListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_foldersize;
        protected RecyclerView recycler_view_list;

        OnClickItemListener onClickItemListener;
        public MyViewHolder(View view,OnClickItemListener onClickItemListener) {
            super(view);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            tv_foldersize = (TextView) view.findViewById(R.id.tv_folder2);
            this.onClickItemListener =onClickItemListener;
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
                .inflate(R.layout.card_album_new, parent, false);
        return new MyViewHolder(itemView,mOnClickItemListener);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_foldersize.setText(al_menu.get(position).getListPhoto().size()+" Pictures");
        ArrayList<PhotoModel> singleSectionItems = al_menu.get(position).getListPhoto();
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(context, singleSectionItems,position);

        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemListDataAdapter);



    }

    @Override
    public int getItemCount() {
        return al_menu.size();
    }

    public interface OnClickItemListener{
        void onClickItem(int position);


    }


}
