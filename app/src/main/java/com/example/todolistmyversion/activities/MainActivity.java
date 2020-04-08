package com.example.todolistmyversion.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todolistmyversion.R;
import com.example.todolistmyversion.adapter.NotesAdapter;
import com.example.todolistmyversion.helpers.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    NotesAdapter notesAdapter;
    DBHelper dbHelper;
    public static final int REQ_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DBHelper(this);
        notesAdapter = new NotesAdapter(dbHelper,this);
        recyclerView.setAdapter(notesAdapter);

    }

    public void addTapped(View view){
        Intent intent = new Intent(MainActivity.this , SaveNoteActivity.class);
        startActivityForResult(intent , REQ_CODE);

    }

    public void clickOnTask(View view){

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            notesAdapter.notifyDataSetChanged();
            //Toast.makeText(this, "Inserted successfully", Toast.LENGTH_LONG).show();
            return;
        }

        //Toast.makeText(this, "Updated successfully", Toast.LENGTH_LONG).show();

        notesAdapter.notifyDataSetChanged();
    }



}



























