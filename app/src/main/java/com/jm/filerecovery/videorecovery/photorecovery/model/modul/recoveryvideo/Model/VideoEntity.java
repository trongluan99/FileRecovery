package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VideoEntity implements Serializable {
    String pathPhoto;
    long lastModifiedPhoto;
    long sizePhoto;
    boolean isCheck= false;
    String typeFile;
    String  timeDuration;

    public VideoEntity(String path, long date, long size, String typeFile, String timeDuration){
        this.pathPhoto = path;
        this.lastModifiedPhoto = date;
        this.sizePhoto = size;
        this.typeFile = typeFile;
        this.timeDuration = timeDuration;
    }
    public long getLastModified(){
        return  lastModifiedPhoto;
    }
    public void setLastModified(long mLastModified){
        this.lastModifiedPhoto = mLastModified;
    }
    public void setSizePhoto(long msizePhoto){
        sizePhoto = msizePhoto;

    }
    public boolean getIsCheck(){
        return isCheck;
    }
    public void setIsCheck(boolean checked){
        isCheck = checked;
    }

    public long getSizePhoto() {
        return sizePhoto;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String path) {
        this.pathPhoto = path;
    }
    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String type) {
        this.typeFile = type;
    }
    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String mtime) {
        this.timeDuration = mtime;
    }
}
