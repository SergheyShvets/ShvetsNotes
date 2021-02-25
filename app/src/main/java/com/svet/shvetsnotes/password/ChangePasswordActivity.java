package com.svet.shvetsnotes.password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.svet.shvetsnotes.R;
import com.svet.shvetsnotes.app.App;
import com.svet.shvetsnotes.app.keystore.Keystore;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText newPasEdit;
    private ImageView imageViewPassword;

    private static boolean checkImage = false;
    private Keystore simpleKeystore;

    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        newPasEdit = findViewById(R.id.newPasEdit);
        imageViewPassword = findViewById(R.id.imageViewPassword);
        imageViewPassword.setImageResource(R.drawable.ic_visibility_off);
        saveBtn = findViewById(R.id.saveBtn);
        simpleKeystore = App.getKeystore();

        //При нажатии на картинку открывается/прячется вводимый пароль
        imageViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkImage) {
                    imageViewPassword.setImageResource(R.drawable.ic_visibility_off);
                    newPasEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    newPasEdit.setSelection(newPasEdit.getText().length());
                    newPasEdit.setHint(R.string.notVisibility);
                    checkImage = false;
                } else {
                    imageViewPassword.setImageResource(R.drawable.ic_visibility_on);
                    newPasEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                    newPasEdit.setSelection(newPasEdit.getText().length());
                    newPasEdit.setHint(R.string.visibility);
                    checkImage = true;
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEdit = newPasEdit.getText().toString();
                if (textEdit.length() == 4) {
                    try {
                        simpleKeystore.saveNew(textEdit);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    newPasEdit.setText("");
                    Toast.makeText(ChangePasswordActivity.this, R.string.new_password, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, R.string.short_password, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}