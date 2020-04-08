package com.example.todolistmyversion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistmyversion.R;
import com.example.todolistmyversion.helpers.DBHelper;
import com.example.todolistmyversion.models.Note;

public class SaveNoteActivity extends AppCompatActivity {

    public static final int INSERT_SUCCESS = 0;
    static int UPDATE_SUCCESS = 1;
    Note mUpdateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_note_activity);

        if (getIntent().getStringExtra("id") != null) {
            DBHelper dbHelper = new DBHelper(this);

            long id = Long.parseLong(getIntent().getStringExtra("id"));
            mUpdateNote = dbHelper.getNote(id);

            EditText etTitle = findViewById(R.id.et_title);

            EditText etBody = findViewById(R.id.et_body);

            etTitle.setText(mUpdateNote.getNoteTitle());
            etBody.setText(mUpdateNote.getNoteBody());

            Button actionButton = findViewById(R.id.btn_action);

            actionButton.setText("Update");
        }
    }

    public void submitTapped(View view) {

        DBHelper db = new DBHelper(this);

        EditText etTitle = findViewById(R.id.et_title);

        EditText etBody = findViewById(R.id.et_body);

        if (mUpdateNote != null) {

            mUpdateNote.setNoteTitle(etTitle.getText().toString());
            mUpdateNote.setNoteBody(etBody.getText().toString());


            db.updateNote(mUpdateNote);

            Intent intent = new Intent();
            setResult(UPDATE_SUCCESS, intent);
            finish();

        } else {
            long id = db.saveNote(etTitle.getText().toString(), etBody.getText().toString());
            // get the newly inserted note from db
            Note note = db.getNote(id);

            if (note != null && (!etTitle.getText().toString().isEmpty()||!etBody.getText().toString().isEmpty())) {
                Intent intent = new Intent();
                setResult(INSERT_SUCCESS, intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "Please enter something to save", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
