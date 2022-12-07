package com.jm.filerecovery.videorecovery.photorecovery.utils;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jm.filerecovery.videorecovery.photorecovery.R;

public class Rate {
    public static void Show(final Context mContext) {
        try {
            if (UtilsApp.isConnectionAvailable(mContext)) {
                if (!PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("Show_rate", false)) {
                    RateApp a = new RateApp(mContext, mContext.getString(R.string.email_feedback), mContext.getString(R.string.app_name));
                    a.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    a.show();
                } else {
                    ((Activity) (mContext)).finish();
                }

            } else {
                ((Activity) (mContext)).finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
            ((Activity) (mContext)).finish();
        }

    }

    public static void Show(final Context mContext, OnResult mOnResult) {
        try {

            if (UtilsApp.isConnectionAvailable(mContext)) {
                boolean showRate = PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("Show_rate", false);
                Log.d("TuanPA38","Show Rate 1111111 showRate = "+showRate);
                if (!showRate) {
                    Log.d("TuanPA38","Show Rate 22222 ");
                    RateApp a = new RateApp(mContext, mContext.getString(R.string.email_feedback), mContext.getString(R.string.app_name),  mOnResult);
                    a.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    a.show();
                } else {
                    Log.d("TuanPA38","Show Rate 3333333 ");
                    if (mOnResult != null)
                        mOnResult.callActionAfter();
                }

            } else {
                Log.d("TuanPA38","Show Rate 4444444 ");
                if (mOnResult != null)
                    mOnResult.callActionAfter();
            }

        } catch (Exception e) {
            Log.d("TuanPA38","Show Rate 555555555 ");
            e.printStackTrace();
            if (mOnResult != null)
                mOnResult.callActionAfter();
        }

    }

    public interface OnResult {
        void callActionAfter();
    }
}
