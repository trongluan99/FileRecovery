package com.jm.filerecovery.videorecovery.photorecovery.model;

public class LanguageModel {
    private String id;
    private int icon;
    private int state;
    private String name;

    public LanguageModel(String id, int icon, int state, String name) {
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
