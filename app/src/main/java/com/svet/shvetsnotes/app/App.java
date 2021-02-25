package com.svet.shvetsnotes.app;

import android.app.Application;

import com.svet.shvetsnotes.app.noteRepository.FileNoteRepository;
import com.svet.shvetsnotes.app.noteRepository.NoteRepository;
import com.svet.shvetsnotes.app.keystore.Keystore;
import com.svet.shvetsnotes.app.keystore.SimpleKeystore;

public class App extends Application {
    private static NoteRepository noteRepository;
    private static Keystore keystore;

    @Override
    public void onCreate() {
        super.onCreate();

        noteRepository = new FileNoteRepository(this);
        keystore = new SimpleKeystore(this);
    }

    public static NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public static Keystore getKeystore() {
        return keystore;
    }
}
