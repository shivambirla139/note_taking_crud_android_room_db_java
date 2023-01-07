package com.appdash.noteapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("select * from note")
    List<Note> getAllNote();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

}
