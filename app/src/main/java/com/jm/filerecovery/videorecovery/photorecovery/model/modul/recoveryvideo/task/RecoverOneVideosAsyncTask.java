package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.TextView;


import com.jm.filerecovery.videorecovery.photorecovery.ui.activity.RestoringDialog;
import com.jm.filerecovery.videorecovery.photorecovery.R;
import com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model.VideoEntity;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.MediaScanner;
import com.jm.filerecovery.videorecovery.photorecovery.utilts.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class RecoverOneVideosAsyncTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getName();
    private VideoEntity mVideo;
    private Context mContext;
    private RestoringDialog progressDialog;
    private OnRestoreListener onRestoreListener;
    TextView tvNumber;
    int count = 0;

    public RecoverOneVideosAsyncTask(Context context, VideoEntity mv, OnRestoreListener mOnRestoreListener) {
        this.mContext = context;
        this.mVideo = mv;
        this.onRestoreListener = mOnRestoreListener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new RestoringDialog(this.mContext);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    protected String doInBackground(String... strAr) {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File sourceFile = new File(mVideo.getPathPhoto());
            File fileDirectory = new File(Utils.getPathSave(mContext,mContext.getString(R.string.restore_folder_path_video)));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Utils.getPathSave(mContext,mContext.getString(R.string.restore_folder_path_video)));
            stringBuilder.append(File.separator);

            stringBuilder.append(getFileName(mVideo.getPathPhoto()));
            File destinationFile = new File(stringBuilder.toString());
            try {
                if (!destinationFile.exists()) {
                    fileDirectory.mkdirs();
                }
                copy(sourceFile, destinationFile);
                if (Build.VERSION.SDK_INT >= 19) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(destinationFile));
                    this.mContext.sendBroadcast(intent);
                }
                new MediaScanner(this.mContext, destinationFile);


            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return null;
    }

    public void copy(File file, File file2) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(file2).getChannel();

        source.transferTo(0, source.size(), destination);
        if (source != null) {
            source.close();
        }
        if (destination!=null){
            destination.close();
        }
    }
    public String getFileName(String  path) {
        String filename=path.substring(path.lastIndexOf("/")+1);
        if (filename.endsWith(".3gp") || filename.endsWith(".mp4")||filename.endsWith(".mkv") ||filename.endsWith(".flv")) {
            return filename;
        }else{
            return filename+".mp4";
        }
    }

    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        try {
            if (this.progressDialog != null&&progressDialog.isShowing()) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
        }catch (Exception e){

        }
        if (null != onRestoreListener) {
            onRestoreListener.onComplete();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        tvNumber = (TextView)progressDialog.findViewById(R.id.tvNumber);
        tvNumber.setText(mContext.getString(R.string.restoring_video));

    }

    public interface OnRestoreListener {
        void onComplete();
    }
}
