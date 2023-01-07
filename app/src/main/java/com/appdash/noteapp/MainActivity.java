package com.appdash.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textEmpty;
    RecyclerView rcNotes;
    FloatingActionButton floatingActionButton;
    ArrayList<Note> allNotes;
    DatabaseHelper databaseHelper;
    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        showData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_note_card);
                EditText editTitle,editDesc;
                Button btnAdd;
                editTitle = dialog.findViewById(R.id.editTitle);
                editDesc = dialog.findViewById(R.id.editDesc);
                dialog.show();
                btnAdd = dialog.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = editTitle.getText().toString().trim();
                        String desc = editDesc.getText().toString().trim();
                        if(!desc.equals("")){
                            databaseHelper.noteDao().insertNote(new Note(title,desc));
                        }
                        dialog.dismiss();
                        showData();
                    }
                });

            }
        });
    }

    public void showData() {
       allNotes = (ArrayList<Note>)databaseHelper.noteDao().getAllNote();
       if(allNotes.size()>0){
           rcNotes.setVisibility(View.VISIBLE);
           textEmpty.setVisibility(View.GONE);
           noteAdapter = new NoteAdapter(this,allNotes,databaseHelper);
           rcNotes.setAdapter(noteAdapter);
       }else{
           rcNotes.setVisibility(View.GONE);
           textEmpty.setVisibility(View.VISIBLE);
       }
    }

    private void init(){
        textEmpty = findViewById(R.id.textEmpty);
        rcNotes = findViewById(R.id.rcNotes);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        rcNotes.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        allNotes = (ArrayList<Note>)DatabaseHelper.getInstance(this).noteDao().getAllNote();
        databaseHelper=DatabaseHelper.getInstance(MainActivity.this);


    }
}