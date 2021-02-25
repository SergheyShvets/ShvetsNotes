package com.svet.shvetsnotes.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE calendarLong != 0 ORDER BY calendarLong ASC")
    List<Note> getAllSortWithDate();

    @Query("SELECT * FROM note WHERE calendarLong = 0 ORDER BY lastChangedLong DESC")
    List<Note> getAllSortWithOutDate();

    @Query("SELECT * FROM note WHERE id = :id")
    Note getById(int id);

    @Insert(onConflict = IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
