package com.lubuteam.sellsource.filerecovery.utilts;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;

public final class FileUtil {

    // By default, Android doesn't provide support for JSON
    public static final String MIME_TYPE_JSON = "application/json";

    @Nullable
    public static String getMimeType(@NonNull Context context, @NonNull Uri uri) {

        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = getExtension(uri.toString());

            if (fileExtension == null) {
                return null;
            }

            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());

            if (mimeType == null) {
                // Handle the misc file extensions
                return handleMiscFileExtensions(fileExtension);
            }
        }
        return mimeType;
    }

    @Nullable
    private static String getExtension(@Nullable String fileName) {

        if (fileName == null || TextUtils.isEmpty(fileName)) {
            return null;
        }

        char[] arrayOfFilename = fileName.toCharArray();
        for (int i = arrayOfFilename.length - 1; i > 0; i--) {
            if (arrayOfFilename[i] == '.') {
                return fileName.substring(i + 1, fileName.length());
            }
        }
        return null;
    }

    @Nullable
    private static String handleMiscFileExtensions(@NonNull String extension) {

        if (extension.equals("json")) {
            return MIME_TYPE_JSON;
        } else {
            return null;
        }
    }

    public static String longToSizeText(long size) {
        long d1 = (long) (size / 1024.0);
        long d2 = (long) (d1 / 1024.0);
        long d3 = (long) (d2 / 1024.0);
        if (size < 1024.0) {
            return size + " Bytes";
        }
        if (d1 < 1024.0) {
            return new BigDecimal(d1).setScale(2, 4).toString() + " KB";
        }
        if (d2 < 1024.0) {
            return new BigDecimal(d2).setScale(2, 4).toString() + " MB";
        } else {
            return new BigDecimal(d3).setScale(2, 4).toString() + " GB";
        }
    }
}