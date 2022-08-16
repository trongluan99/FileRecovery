package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryaudio.Model;

import java.util.ArrayList;

public class AlbumAudio {
    String str_folder;
    ArrayList<AudioModel> listPhoto;
    long lastModified;

    public long getLastModified(){
        return  lastModified;
    }
    public void setLastModified(long mLastModified){
        this.lastModified = mLastModified;
    }
    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<AudioModel> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<AudioModel> mList) {
        this.listPhoto = mList;
    }
}
