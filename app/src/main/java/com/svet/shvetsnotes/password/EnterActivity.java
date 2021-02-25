package com.svet.shvetsnotes.password;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.svet.shvetsnotes.R;
import com.svet.shvetsnotes.app.App;
import com.svet.shvetsnotes.app.keystore.Keystore;
import com.svet.shvetsnotes.notes.NotesListActivity;

import java.security.NoSuchAlgorithmException;

public class EnterActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private TextView textViewPin;
    private RadioButton inputPin1;
    private RadioButton inputPin2;
    private RadioButton inputPin3;
    private RadioButton inputPin4;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnClear;
    private Keystore simpleKeystore;
    private static String inputPasswordString = ""; //строка воодимого пароля.
    private static int checkInputNumbers = 0; //проверяем количество введенных цифр.
    private static boolean checkExistPassword; //Проверяет сохранен ли пароль или нет.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        textViewPin = findViewById(R.id.textViewPin);
        inputPin1 = findViewById(R.id.inputPin1);
        inputPin2 = findViewById(R.id.inputPin2);
        inputPin3 = findViewById(R.id.inputPin3);
        inputPin4 = findViewById(R.id.inputPin4);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnClear = findViewById(R.id.btnClear);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        simpleKeystore = App.getKeystore();
        checkExistPassword = simpleKeystore.hasPin();
        if (!checkExistPassword) {
            textViewPin.setText(R.string.input_first_pin);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                inputPasswordString += "0";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn1:
                inputPasswordString += "1";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn2:
                inputPasswordString += "2";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn3:
                inputPasswordString += "3";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn4:
                inputPasswordString += "4";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn5:
                inputPasswordString += "5";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn6:
                inputPasswordString += "6";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn7:
                inputPasswordString += "7";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn8:
                inputPasswordString += "8";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btn9:
                inputPasswordString += "9";
                checkInputNumbers++;
                inputedSequencedNumberPin();
                break;
            case R.id.btnClear:

                if (checkInputNumbers > 0) {
                    inputPasswordString = inputPasswordString.substring(0, inputPasswordString.length() - 1);
                    inputedSequencedNumberPin();
                    checkInputNumbers--;
                }

                break;


        }

    }


    //RadioButton 1,2,3,4 будут регестрировать ввод новой цифры в соотвествии с переменной checkInputNumbers. При нажатии кнопки "стирании", checkInputNumbers будет уменьшаться на 1 и выключать включенный RadioButton.
    public void inputedSequencedNumberPin() {

        while (checkInputNumbers > 4) {
            inputPasswordString = inputPasswordString.substring(0, inputPasswordString.length() - 1);
            checkInputNumbers--;
        }

        switch (checkInputNumbers) {
            case 1:
                if (inputPin1.isChecked()) {
                    inputPin1.setChecked(false);
                } else {
                    inputPin1.setChecked(true);
                }

                break;
            case 2:
                if (inputPin2.isChecked()) {
                    inputPin2.setChecked(false);
                } else {
                    inputPin2.setChecked(true);
                }
                break;
            case 3:
                if (inputPin3.isChecked()) {
                    inputPin3.setChecked(false);
                } else {
                    inputPin3.setChecked(true);
                }
                break;
            case 4: // При вводе последней цифре надо сверить пароль( при наличии) или сохранить пароль, при отсуствии. И перейти в другое Activity/
                if (checkExistPassword) {
                    inputPin4.setChecked(true);
                    checkPassword();
                } else {
                    inputPin4.setChecked(true);
                    createPasswordSharedPreference();
                }

                break;
        }

    }

    private void checkPassword() {
        Boolean checkPassword = simpleKeystore.checkPin(inputPasswordString);
        if (checkPassword) {
            Intent intent = new Intent(EnterActivity.this, NotesListActivity.class);
            startActivity(intent);

        } else {

            Toast.makeText(EnterActivity.this, R.string.wrong_password, Toast.LENGTH_LONG).show();
        }
        checkInputNumbers = 0;
        inputPasswordString = "";
        inputPin1.setChecked(false);
        inputPin2.setChecked(false);
        inputPin3.setChecked(false);
        inputPin4.setChecked(false);

    }

    private void createPasswordSharedPreference() {
        try {
            simpleKeystore.saveNew(inputPasswordString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Toast.makeText(EnterActivity.this, R.string.new_password, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EnterActivity.this, NotesListActivity.class);
        startActivity(intent);
        finish();
    }
}