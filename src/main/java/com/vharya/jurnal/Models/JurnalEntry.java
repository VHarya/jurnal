package com.vharya.jurnal.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JurnalEntry {
    private int id;
    private String content;
    private Date createdDate;
    private Date updateDate;

    public JurnalEntry() {
        //
    }

    public JurnalEntry(int id, String content, Date createdDate, Date updateDate) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MMM/yyyy HH:mm");
        return dateFormat.format(createdDate);
    }

    public String getUpdateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MMM/yyyy HH:mm");
        return dateFormat.format(updateDate);
    }
}
