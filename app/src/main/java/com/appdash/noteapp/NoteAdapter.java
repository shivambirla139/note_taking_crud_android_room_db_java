package com.appdash.noteapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.browse.MediaBrowser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context ;
    ArrayList<Note> allNotes;
    DatabaseHelper databaseHelper;
    public NoteAdapter(Context context, ArrayList<Note> allNotes, DatabaseHelper databaseHelper){
        this.context = context;
        this.allNotes = allNotes;
        this.databaseHelper = databaseHelper;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.note_card,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTitle.setText(allNotes.get(position).getTitle());
        holder.textDesc.setText(allNotes.get(position).getDesc());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("You want to delete ?")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseHelper.noteDao().deleteNote(new Note(
                                        allNotes.get(position).getId(),
                                        allNotes.get(position).getTitle(),
                                        allNotes.get(position).getDesc()
                                ));
                                ((MainActivity)context).showData();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity)context).showData();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_note_card);
                EditText editTitle = dialog.findViewById(R.id.editTitle);
                EditText editDesc = dialog.findViewById(R.id.editDesc);
                Button btnEdit = dialog.findViewById(R.id.btnAdd);
                btnEdit.setText("EDit NOTe");
                editTitle.setText(allNotes.get(position).getTitle());
                editDesc.setText(allNotes.get(position).getDesc());
                dialog.show();
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = editTitle.getText().toString().trim();
                        String desc = editDesc.getText().toString().trim();
                        databaseHelper.noteDao().updateNote(new Note(allNotes.get(position).getId(),title,desc));
                        ((MainActivity)context).showData();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textTitle,textDesc;
        ImageView btnEdit,btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
