package com.lubuteam.sellsource.filerecovery.utilts;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;

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

}
