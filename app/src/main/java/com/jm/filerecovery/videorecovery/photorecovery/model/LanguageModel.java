package com.jm.filerecovery.videorecovery.photorecovery.model;

public class LanguageModel {
    private String name;
    private String id;

    private boolean state;
    private int icon;



    public LanguageModel(String name, String id, boolean state, int icon) {
        this.id = id;
        this.icon = icon;
        this.state = state;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
