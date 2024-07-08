package com.vharya.jurnal.Models;

public class User {
    private int id = -1;
    private String name = "";
    private String imageFilepath = "";
    private boolean isProtected = false;

    public User() {
        //
    }

    public User(int id, String name, String imageFilepath) {
        this.id = id;
        this.name = name;
        this.imageFilepath = imageFilepath;
    }

    public User(int id, String name, String imageFilepath, boolean isProtected) {
        this.id = id;
        this.name = name;
        this.imageFilepath = imageFilepath;
        this.isProtected = isProtected;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilepath() {
        return imageFilepath;
    }

    public boolean isProtected() {
        return isProtected;
    }
}
