package com.svet.shvetsnotes.app.noteRepository;

import com.svet.shvetsnotes.db.Note;

import java.util.List;

public interface NoteRepository  {
    Note getNoteById(int id);

    List<Note> getNotes();

    void saveNote(Note note);

    void correctNote(int id, String title, String subtitle, Long calendarLong, Long lastRecordingToLong);

    void deleteById(int id);
}
