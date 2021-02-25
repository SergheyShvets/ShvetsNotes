package com.svet.shvetsnotes.app.noteRepository;

import android.content.Context;

import androidx.room.Room;

import com.svet.shvetsnotes.db.AppDataBase;
import com.svet.shvetsnotes.db.Note;
import com.svet.shvetsnotes.db.NoteDao;

import java.util.ArrayList;
import java.util.List;

public class FileNoteRepository implements NoteRepository {
    private List<Note> notes;
    private final NoteDao noteDao;

    public FileNoteRepository(Context context) {
        AppDataBase db = Room.databaseBuilder(context, AppDataBase.class, "database2")
                .allowMainThreadQueries()
                .build();
        noteDao = db.noteDao();
        loadFromDb();
    }


    @Override
    public Note getNoteById(int id) {
        return notes.get(id);
    }

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void saveNote(Note note) {
        noteDao.insert(note);
        loadFromDb();
    }

    @Override
    public void correctNote(int id, String title, String subtitle, Long calendarLong, Long lastRecordingToLong) {
        notes.get(id).setTitle(title);
        notes.get(id).setSubtitle(subtitle);
        notes.get(id).setCalendarLong(calendarLong);
        notes.get(id).setLastChangedLong(lastRecordingToLong);
        noteDao.update(notes.get(id));
        loadFromDb();
    }

    @Override
    public void deleteById(int id) {
        noteDao.delete(notes.get(id));
        loadFromDb();
    }

    private void loadFromDb() {
        List<Note> notesWithDate;
        List<Note> notesWithOutDate;
        notes = new ArrayList<>();
        try {
            notesWithDate = noteDao.getAllSortWithDate();
            notesWithOutDate = noteDao.getAllSortWithOutDate();
            notes.addAll(notesWithDate);
            notes.addAll(notesWithOutDate);
        } catch (Exception e) {
            notes = null;
        }
    }
}
