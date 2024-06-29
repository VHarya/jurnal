package com.vharya.jurnal;

public class GlobalSingleton {
    private final static GlobalSingleton instance = new GlobalSingleton();

    private int loggedInUserId;

    private GlobalSingleton() {}

    public static GlobalSingleton getInstance() {
        return instance;
    }

    public int getUserId() {
        return loggedInUserId;
    }

    public void setUserId(int userId) {
        this.loggedInUserId = userId;
    }
}
