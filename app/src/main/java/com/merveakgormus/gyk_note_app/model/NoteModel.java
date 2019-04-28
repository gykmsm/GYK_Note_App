package com.merveakgormus.gyk_note_app.model;

import java.util.Date;

public class NoteModel {
    private String title;
    private String content;
    private String createdDate;

    public NoteModel(String title, String content, String createdDate) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
