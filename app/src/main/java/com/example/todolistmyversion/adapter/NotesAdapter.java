package com.example.todolistmyversion.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistmyversion.R;
import com.example.todolistmyversion.activities.ItemClickListener;
import com.example.todolistmyversion.activities.SaveNoteActivity;
import com.example.todolistmyversion.helpers.DBHelper;
import com.example.todolistmyversion.models.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    DBHelper dbHelper;

    Context mContext;

    public NotesAdapter(DBHelper dbHelper, Context mContext) {
        this.dbHelper = dbHelper;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notes_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int i) {
        final Note note = dbHelper.getAllNotes().get(i);

        holder.title.setText(note.getNoteTitle());
        holder.body.setText(note.getNoteBody());

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteNote(note);
                notifyDataSetChanged();
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Intent intent = new Intent(mContext, SaveNoteActivity.class);
                intent.putExtra("id", String.valueOf(note.getNoteId()));
                ((Activity) mContext).startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dbHelper.getAllNotes().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView body;
        ImageButton delete_btn;

        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView2);
            body = itemView.findViewById(R.id.textView3);
            delete_btn = itemView.findViewById(R.id.delete_btn);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.OnClick(view, getAdapterPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1){
            Toast.makeText(mContext, "Updated successfully", Toast.LENGTH_LONG).show();

            notifyDataSetChanged();
        }

    }

}







