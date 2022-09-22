package com.jm.filerecovery.videorecovery.photorecovery.utilts;

import android.content.Context;

import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.LanguageModel;

import java.util.ArrayList;
import java.util.List;

public class GlobalAppCache {
    private static GlobalAppCache instance;
    private Context mContext;
    private final List<LanguageModel> languageModelList = new ArrayList<>();

    private GlobalAppCache(Context context) {
        this.mContext = context;
        addListLanguages();
    }

    private void addListLanguages() {
        languageModelList.add(new LanguageModel("en", R.drawable.img_language_en, 0, "English"));
        languageModelList.add(new LanguageModel("hi", R.drawable.img_language_india, 0, "India"));
        languageModelList.add(new LanguageModel("in", R.drawable.img_language_indo, 0, "Indonesia"));
        languageModelList.add(new LanguageModel("zh_CN", R.drawable.img_language_china, 0, "China - 简体汉字"));
        languageModelList.add(new LanguageModel("zh_TW", R.drawable.img_language_china, 0, "China - 繁體漢字"));
        languageModelList.add(new LanguageModel("pt", R.drawable.img_language_pt, 0, "Portugal"));
        languageModelList.add(new LanguageModel("es", R.drawable.img_language_spanish, 0, "Spanish"));
        languageModelList.add(new LanguageModel("ja", R.drawable.img_language_japan, 0, "Japan"));
        languageModelList.add(new LanguageModel("ko", R.drawable.img_language_korean, 0, "Korea"));
        languageModelList.add(new LanguageModel("vi", R.drawable.img_language_vi, 0, "Vietnam"));
        languageModelList.add(new LanguageModel("ru", R.drawable.img_language_ru, 0, "Russia"));
        languageModelList.add(new LanguageModel("de", R.drawable.img_language_de, 0, "Germany"));
        languageModelList.add(new LanguageModel("bn", R.drawable.img_language_bn, 0, "Bangladesh"));
        languageModelList.add(new LanguageModel("th", R.drawable.img_language_th, 0, "Thailand"));
        languageModelList.add(new LanguageModel("my", R.drawable.img_language_my, 0, "Myanmar"));
        languageModelList.add(new LanguageModel("ms", R.drawable.img_language_ms, 0, "Malaysia"));
        languageModelList.add(new LanguageModel("it", R.drawable.img_language_it, 0, "Italy"));
    }

    public List<LanguageModel> getLanguageModelList() {
        return languageModelList;
    }

    public static GlobalAppCache getInstance(Context context) {
        if (instance == null) {
            instance = new GlobalAppCache(context);
        }
        return instance;
    }

}
