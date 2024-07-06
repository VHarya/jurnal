package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;

public class GlobalSingleton {
    private final static GlobalSingleton instance = new GlobalSingleton();

    private Integer loggedInUserId;
    private JurnalEntry selectedEntry;

    private GlobalSingleton() {}

    public static GlobalSingleton getInstance() {
        return instance;
    }

    public void clearData() {
        setUserId(null);
        setSelectedJurnalEntry(null);
    }

    public int getUserId() {
        return loggedInUserId;
    }

    public void setUserId(Integer userId) {
        this.loggedInUserId = userId;
    }

    public JurnalEntry getJurnalEntry() {
        return selectedEntry;
    }

    public void setSelectedJurnalEntry(JurnalEntry entry) {
        this.selectedEntry = entry;
    }
}
