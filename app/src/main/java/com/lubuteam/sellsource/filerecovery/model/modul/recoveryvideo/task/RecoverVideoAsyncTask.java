package com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.TextView;

import com.lubuteam.sellsource.filerecovery.ui.activity.LoadingDialog;
import com.lubuteam.sellsource.filerecovery.R;
import com.lubuteam.sellsource.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.lubuteam.sellsource.filerecovery.utilts.MediaScanner;
import com.lubuteam.sellsource.filerecovery.utilts.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class RecoverVideoAsyncTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getName();
    private ArrayList<VideoModel> listPhoto;
    private Context mContext;
    private LoadingDialog progressDialog;
    private OnRestoreListener onRestoreListener;
    TextView tvNumber;
    int count = 0;

    public RecoverVideoAsyncTask(Context context, ArrayList<VideoModel> mList, OnRestoreListener mOnRestoreListener) {
        this.mContext = context;
        this.listPhoto = mList;
        this.onRestoreListener = mOnRestoreListener;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new LoadingDialog(this.mContext);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    protected String doInBackground(String... strAr) {
        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int strArr = 0; strArr < this.listPhoto.size(); strArr++) {
            File sourceFile = new File(this.listPhoto.get(strArr).getPathPhoto());
            File fileDirectory = new File(Utils.getPathSave(mContext,mContext.getString(R.string.restore_folder_path_video)));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Utils.getPathSave(mContext,mContext.getString(R.string.restore_folder_path_video)));
            stringBuilder.append(File.separator);

            stringBuilder.append(getFileName(listPhoto.get(strArr).getPathPhoto()));
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
                count = strArr+1;
                publishProgress(count);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

//    public String getFileName(int i) {
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.US);
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(simpleDateFormat.format(date));
//        stringBuilder.append(i);
//        stringBuilder.append(".png");
//        return String.valueOf(stringBuilder.toString());
//    }

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
        tvNumber.setText(String.format(mContext.getResources().getString(R.string.restoring_number_format), values[0]));

    }

    public interface OnRestoreListener {
        void onComplete();
    }
}
