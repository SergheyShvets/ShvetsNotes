package com.svet.shvetsnotes.notes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.svet.shvetsnotes.R;
import com.svet.shvetsnotes.app.App;
import com.svet.shvetsnotes.app.noteRepository.NoteRepository;
import com.svet.shvetsnotes.db.Note;

import java.util.Calendar;

public class NotesAdapter extends BaseAdapter {
    private final NoteRepository notesList;
    private final Context context;
    private final LayoutInflater inflater;

    NotesAdapter(Context context) {
        this.context = context;
        notesList = App.getNoteRepository();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notesList.getNotes().size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.getNoteById(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void deleteNote(int id) {
        notesList.deleteById(id);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.card_view_content, parent, false);
        }

        Note note = notesList.getNoteById(position);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        title.setText(note.getTitle());
        subtitle.setText(note.getSubtitle());
        dateTextView.setText(note.calendarDateToString(context));

        CardView cardView = view.findViewById(R.id.cardView);
        LinearLayout linearLayout = view.findViewById(R.id.linerLay);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        if (!(note.getCalendarLong() == 0)) {
            if (note.getCalendarDate().before(calendar)) {
                cardView.setCardBackgroundColor(Color.RED);
            } else {
                cardView.setCardBackgroundColor(Color.WHITE);
            }
        }
        if (note.isNotVisibleText()) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
