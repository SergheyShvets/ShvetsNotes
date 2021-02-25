package com.svet.shvetsnotes.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.svet.shvetsnotes.R;
import com.svet.shvetsnotes.app.App;
import com.svet.shvetsnotes.app.noteRepository.NoteRepository;
import com.svet.shvetsnotes.db.Note;

import java.util.Calendar;


public class RecordingActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editSubtitle;
    private EditText editTextDate;
    private CheckBox checkDeadlineOn;
    private ImageButton imageBtnCalendar;
    private Calendar dateAndTime = Calendar.getInstance();

    private final String INTENT_DATA = "noteOfAdapter";
    private final int RESULT_NOT_OK = 0;
    private static String titleOfItemData;
    private static String subtitleOfItemData;
    private static final NoteRepository notes = App.getNoteRepository();
    private static int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recording, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int RESULT_OK = 1;
        switch (item.getItemId()) {
            case R.id.action_save:
                forSaveResult();
                intent.putExtra(INTENT_DATA, id);
                setResult(RESULT_OK, intent);
                break;
            default:
                Toast.makeText(RecordingActivity.this, "Запись не сохранена", Toast.LENGTH_LONG).show();
        }
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        editTitle = findViewById(R.id.editTitle);
        editSubtitle = findViewById(R.id.editSubtitle);
        editTextDate = findViewById(R.id.editTextDate);
        checkDeadlineOn = findViewById(R.id.checkDeadlineOn);
        imageBtnCalendar = findViewById(R.id.imageBtnDate);

        Intent intent = getIntent();
        id = intent.getIntExtra(INTENT_DATA, -1);
        Note note;
        if (id == -1) {
            note = new Note(null, null, null, null);
        } else {
            note = notes.getNoteById(id);
            titleOfItemData = note.getTitle();
            subtitleOfItemData = note.getSubtitle();
            String textDateOfItemData = note.calendarDateToString(this);
            if (!textDateOfItemData.equals("")) {
                checkDeadlineOn.setChecked(true);
                editTextDate.setEnabled(true);
                imageBtnCalendar.setClickable(true);
                dateAndTime = note.getCalendarDate();
            }
            editTitle.setText(titleOfItemData);
            editSubtitle.setText(subtitleOfItemData);
            editTextDate.setText(textDateOfItemData);

        }

        checkDeadlineOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDeadlineOn.isChecked()) {
                    dateAndTime = Calendar.getInstance();
                    setInitialDateTime();
                    editTextDate.setEnabled(true);
                    imageBtnCalendar.setClickable(true);
                } else {
                    editTextDate.setEnabled(false);
                    editTextDate.setText("");
                    imageBtnCalendar.setClickable(false);
                }
            }
        });

        imageBtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(RecordingActivity.this, t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
                new DatePickerDialog(RecordingActivity.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show(); }
        });

        if (!checkDeadlineOn.isChecked()) {
            imageBtnCalendar.setClickable(false);
        }
    }

    final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {
        String timeDate = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);

        if (timeDate != null) {
            editTextDate.setText(timeDate);
        }
    }

    private void forSaveResult() {
        long convertCalendarToLong;
        titleOfItemData = editTitle.getText().toString();
        subtitleOfItemData = editSubtitle.getText().toString();
        long lastRecordingToLong = dateAndTime.getTimeInMillis();
        if (checkDeadlineOn.isChecked()) {
            convertCalendarToLong = dateAndTime.getTimeInMillis();
        } else {
            convertCalendarToLong = 0;
        }
        if (id == -1) {
            notes.saveNote(new Note(titleOfItemData, subtitleOfItemData, convertCalendarToLong, lastRecordingToLong));
        } else {
            notes.correctNote(id, titleOfItemData, subtitleOfItemData, convertCalendarToLong, lastRecordingToLong);
        }
    }
}
