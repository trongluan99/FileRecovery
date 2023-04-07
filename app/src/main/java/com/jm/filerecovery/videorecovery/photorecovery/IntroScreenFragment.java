package com.jm.filerecovery.videorecovery.photorecovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class IntroScreenFragment extends Fragment {
    private String des;
    private int img_drawable;
    private ImageView img_intro;
    private String title;
    private TextView tv_intro;
    private TextView tv_title;

    public static IntroScreenFragment newInstant(int i, String str, String str2) {
        IntroScreenFragment introScreenFragment = new IntroScreenFragment();
        introScreenFragment.img_drawable = i;
        introScreenFragment.title = str;
        introScreenFragment.des = str2;
        return introScreenFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_screen_intro, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.img_intro = (ImageView) view.findViewById(R.id.img_intro);
        this.tv_intro = (TextView) view.findViewById(R.id.tv_intro);
        this.tv_title = (TextView) view.findViewById(R.id.tv_title);
        this.img_intro.setImageResource(this.img_drawable);
        this.tv_intro.setText(this.des);
        this.tv_title.setText(this.title);
    }
}
