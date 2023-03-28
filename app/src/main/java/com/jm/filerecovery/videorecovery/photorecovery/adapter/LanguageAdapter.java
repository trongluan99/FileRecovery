package com.jm.filerecovery.videorecovery.photorecovery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;
import com.jm.filerecovery.videorecovery.photorecovery.ui.IClickLanguage;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    private Context mContext;
    private List<LanguageModel> languageModelList;
    private IClickLanguage iClickLanguage;

    public LanguageAdapter(Context mContext, List<LanguageModel> languageModelList, IClickLanguage mItemClickListener) {
        this.mContext = mContext;
        this.languageModelList = languageModelList;
        this.iClickLanguage = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_language, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binData(languageModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return languageModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_language;
        TextView tv_title_language;
        CheckBox checkbox_language;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            img_language = itemView.findViewById(R.id.img_language);
            tv_title_language = itemView.findViewById(R.id.tv_title_language);
            checkbox_language = itemView.findViewById(R.id.checkbox_language);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 10, 5, 10);
            itemView.setLayoutParams(lp);
        }
        public void binData(LanguageModel languageModel) {
            if (languageModel != null) {
                img_language.setImageDrawable(mContext.getResources().getDrawable(languageModel.getIcon()));
                img_language.setBorderColor(mContext.getResources().getColor(R.color.color_9E9E9E));
                tv_title_language.setText(languageModel.getName());
                if (languageModel.getState()) {
                    checkbox_language.setChecked(true);
                } else {
                    checkbox_language.setChecked(false);
                }
                container.setOnClickListener(v -> iClickLanguage.onClick(languageModel));
                checkbox_language.setOnClickListener(v -> iClickLanguage.onClick(languageModel));
            }
        }
    }



    public void setSelectLanguage(LanguageModel model) {
        for (LanguageModel data : languageModelList) {
            if (data.getName().equals(model.getName())) {
                data.setState(true);
            } else {
                data.setState(false);
            }
        }
        notifyDataSetChanged();
    }
}
