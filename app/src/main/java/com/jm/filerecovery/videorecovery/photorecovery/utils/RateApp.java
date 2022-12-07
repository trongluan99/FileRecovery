package com.jm.filerecovery.videorecovery.photorecovery.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.utils.Rate;
import com.jm.filerecovery.videorecovery.photorecovery.utils.UtilsApp;

public class RateApp extends Dialog {

    private Rate.OnResult mOnResult;
    Context mContext;
    String mEmail, mTitleEmail;


    public RateApp(Context context, String email, String TitleEmail) {
        super(context);
        mContext = context;
        mEmail = email;
        mTitleEmail = TitleEmail;


    }

    public RateApp(Context context, String email, String TitleEmail, Rate.OnResult mOnResult) {
        super(context);
        mContext = context;
        mEmail = email;
        mTitleEmail = TitleEmail;

        this.mOnResult = mOnResult;
    }

    int start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_rate_app);
        setCancelable(true);
        AppCompatImageView iconFell = findViewById(R.id.icon_fell);
        ConstraintLayout constraint_rate = findViewById(R.id.constraint_rate);
        TextView textTitle = findViewById(R.id.text_title_rate);
        TextView textContent = findViewById(R.id.text_content_rate);
        AppCompatImageView rate1 = findViewById(R.id.img_rate1);
        AppCompatImageView rate2 = findViewById(R.id.img_rate2);
        AppCompatImageView rate3 = findViewById(R.id.img_rate3);
        AppCompatImageView rate4 = findViewById(R.id.img_rate4);
        AppCompatImageView rate5 = findViewById(R.id.img_rate5);
        TextView btn_cancel = findViewById(R.id.btn_cancel);
        TextView rate = findViewById(R.id.btn_rate);
        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                rate3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                rate4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                rate5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                iconFell.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_sad));
                rate.setVisibility(View.VISIBLE);
                start = 1;
            }
        });
        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                rate4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                rate5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                iconFell.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_sad));
                rate.setVisibility(View.VISIBLE);
                start = 2;
            }
        });
        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                rate5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                iconFell.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_sad));
                rate.setVisibility(View.VISIBLE);
                start = 3;
            }
        });
        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_default));
                iconFell.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_smile));
                rate.setVisibility(View.VISIBLE);
                start = 4;
            }
        });
        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                rate5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.rate_yellow));
                iconFell.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_happy));
                rate.setVisibility(View.VISIBLE);
                start = 5;
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((Activity) (mContext)).finish();
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rate.getText().equals(mContext.getResources().getString(R.string.go_to_google_play))) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("Show_rate", true);
                    editor.commit();
                    UtilsApp.RateApp(mContext);
                    dismiss();
                } else {
                    if (start >= 4) {
                        iconFell.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_heart));
                        textTitle.setText(mContext.getResources().getString(R.string.tks_to_rate));
                        rate.setText(mContext.getResources().getString(R.string.go_to_google_play));
                        textContent.setVisibility(View.VISIBLE);
                        btn_cancel.setVisibility(View.GONE);
                    } else {
                        if (start != 0) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("Show_rate", true);
                            editor.commit();
                            dismiss();
                        } else {
                            dismiss();
                        }
                    }
                }
            }
        });

    }
}
