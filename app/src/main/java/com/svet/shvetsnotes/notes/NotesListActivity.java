package com.svet.shvetsnotes.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svet.shvetsnotes.R;
import com.svet.shvetsnotes.password.ChangePasswordActivity;

public class NotesListActivity extends AppCompatActivity {
    private NotesAdapter adapter;
    private final String INTENT_DATA = "noteOfAdapter";
    private final int REQUEST_CODE_NEW_RECORD = 1;
    private final int REQUEST_CODE_REWRITING_RECORD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentMenu = new Intent(NotesListActivity.this, ChangePasswordActivity.class);
        startActivity(intentMenu);
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);
        adapter = new NotesAdapter(this);
        listView.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesListActivity.this, RecordingActivity.class);
                intent.putExtra(INTENT_DATA, -1);
                startActivityForResult(intent, REQUEST_CODE_NEW_RECORD);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotesListActivity.this, RecordingActivity.class);
                intent.putExtra(INTENT_DATA, position);
                startActivityForResult(intent, REQUEST_CODE_REWRITING_RECORD);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotesListActivity.this);
                builder.setTitle("Warning")
                        .setMessage("Are you sure you want to delete this note?")
                        .setCancelable(true)
                        .setIcon(R.drawable.ic_delete)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NotesListActivity.this, "Запись не удалена", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.deleteNote(position);
                                Toast.makeText(NotesListActivity.this, "Запись удалена успешно", Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        int RESULT_OK = 1;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_RECORD:
                    Toast.makeText(NotesListActivity.this, "Новая запись сохранена", Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_CODE_REWRITING_RECORD:
                    Toast.makeText(NotesListActivity.this, "Запись изменена", Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            Toast.makeText(NotesListActivity.this, "Запись не сохранена", Toast.LENGTH_LONG).show();
        }
        adapter.notifyDataSetChanged();
    }
}
