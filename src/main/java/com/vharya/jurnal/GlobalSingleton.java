package com.vharya.jurnal;

import com.vharya.jurnal.Models.JurnalEntry;
import com.vharya.jurnal.Models.User;

public class GlobalSingleton {
    private final static GlobalSingleton instance = new GlobalSingleton();

    private User selectedUser;
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

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public JurnalEntry getSelectedJurnalEntry() {
        return selectedEntry;
    }

    public void setSelectedJurnalEntry(JurnalEntry entry) {
        this.selectedEntry = entry;
    }
}
