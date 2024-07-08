package com.vharya.jurnal.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JurnalEntry {
    private int id;
    private String content;
    private String createdDate;

    public JurnalEntry() {
        //
    }

    public JurnalEntry(int id, String content, String createdDate) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
