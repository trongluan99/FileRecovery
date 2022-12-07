package com.jm.filerecovery.videorecovery.photorecovery.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static String formatSize(long size) {
        if (size <= 0)
            return "";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    public static String getFileName(String  path) {
        String filename=path.substring(path.lastIndexOf("/")+1);
        return filename;
    }
    public static File[] getFileList(String str) {
        File file = new File(str);
        if (!file.isDirectory()) {
            return new File[0];
        }

        return file.listFiles();
    }
    public static boolean checkSelfPermission(Activity activity, String s) {
        if (isAndroid23()) {
            return ContextCompat.checkSelfPermission(activity, s) == 0;
        } else {
            return true;
        }
    }
    public static boolean isAndroid23() {
        return android.os.Build.VERSION.SDK_INT >=23;
    }

    public static String getFileTitle(String  path) {

//        String filename=path.substring(path.lastIndexOf("/")+1);
        String filename=path.substring(path.lastIndexOf("/")+1);
        return filename;


    }
    public static String getPathSave(Context c,String path){
        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStorageDirectory());
        sbDirectory.append(File.separator);
        sbDirectory.append(path);
        return  sbDirectory.toString();
    }

    public static String convertDuration(long m) {

        String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(m),
                TimeUnit.MILLISECONDS.toSeconds(m) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(m))
        );

        return hms;

    }

    public static void setStatusBarHomeTransparent(FragmentActivity activity) {
        Window window = activity.getWindow();
//            window.setNavigationBarColor(Color.BLACK);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //make fully Android Transparent Status bar
        setWindowFlag((AppCompatActivity) activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(AppCompatActivity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static int getHeightStatusBar(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
