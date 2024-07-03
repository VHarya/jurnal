package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;

public class GlobalSingleton {
    private final static GlobalSingleton instance = new GlobalSingleton();

    private int loggedInUserId;
    private JurnalEntry selectedEntry;

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

    public JurnalEntry getJurnalEntry() {
        return selectedEntry;
    }

    public void setJurnalEntry(JurnalEntry entry) {
        this.selectedEntry = entry;
    }
}
