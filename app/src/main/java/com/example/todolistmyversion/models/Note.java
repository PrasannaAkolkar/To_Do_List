package com.example.todolistmyversion.models;

public class Note {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_NOTE_ID = "noteId";
    public static final String COLUMN_NOTE_TITLE = "noteTitle";
    public static final String COLUMN_NOTE_BODY = "noteBody";
    public static final String COLUMN_CREATED_AT = "createdAt";

    private int noteId;
    private String noteTitle;
    private String noteBody;
    private String createdAt;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE_TITLE + " TEXT,"
                    + COLUMN_NOTE_BODY + " TEXT,"
                    + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Note() {
    }

    public Note(int noteId, String noteTitle, String noteBody, String createdAt) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.createdAt = createdAt;
    }



    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
