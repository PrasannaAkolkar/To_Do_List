package com.example.todolistmyversion.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.todolistmyversion.models.Note;

import java.sql.RowId;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_database";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }


    public long saveNote(String title, String body) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();



                values.put(Note.COLUMN_NOTE_TITLE, title);

                values.put(Note.COLUMN_NOTE_BODY, body);


            long id = sqLiteDatabase.insert(Note.TABLE_NAME, null, values);

            sqLiteDatabase.close();
            return id;

    }

    public Note getNote(long id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Note.TABLE_NAME, new String[]{Note.COLUMN_NOTE_ID, Note.COLUMN_NOTE_TITLE, Note.COLUMN_NOTE_BODY, Note.COLUMN_CREATED_AT}, Note.COLUMN_NOTE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_TITLE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_BODY)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_CREATED_AT)));
        cursor.close();
        return note;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_CREATED_AT + " DESC";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_NOTE_ID)));
                note.setNoteTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_TITLE)));
                note.setNoteBody(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_BODY)));
                note.setCreatedAt(cursor.getString(cursor.getColumnIndex(Note.COLUMN_CREATED_AT)));

                notes.add(note);
            } while (cursor.moveToNext());

        }

        sqLiteDatabase.close();
        return notes;

    }

    public int updateNote(Note note) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(Note.COLUMN_NOTE_BODY, note.getNoteBody());
        values.put(Note.COLUMN_NOTE_ID, note.getNoteId());

        // updating row
        return sqLiteDatabase.update(Note.TABLE_NAME, values, Note.COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
    }

    public void deleteNote(Note note) {

      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Note.TABLE_NAME, Note.COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
        sqLiteDatabase.close();


    }

}