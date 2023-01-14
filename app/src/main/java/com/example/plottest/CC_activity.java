package com.example.plottest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;


public class CC_activity extends AppCompatActivity {

    Button transfer;
    EditText input_system;
    EditText input_new_system;
    EditText input_digit;
    TextView result;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc);
        input_digit = findViewById(R.id.InputNumber);
        input_system = findViewById(R.id.System);
        input_new_system = findViewById(R.id.NewSystem);
        transfer = findViewById(R.id.Transfer);
        result = findViewById(R.id.res);
        clear = findViewById(R.id.clear);
    }

    public void onTransferClick(View view) {
        long number; // вводимое число
        int system; //номер системы счисления
        int new_system; //номер системы в которой число

        String sys = String.valueOf(input_system.getText());
        String new_sys = String.valueOf(input_new_system.getText());
        String digit = String.valueOf(input_digit.getText());

        if (sys.length() == 0 || sys.matches("0+") ||
                new_sys.length() == 0 || new_sys.matches("0+") ||
                digit.length() == 0 || digit.matches("0+")) {
            result.setText("Ошибка");
            input_system.setText("");
            input_new_system.setText("");
        }
        else{
            boolean flag = true;
            //Система счисления
            system = Integer.parseInt(sys);
            new_system = Integer.parseInt(new_sys);

            if (system > 1 && system < 37 &&
                    new_system > 1 && new_system < 37) {
                // Перевод в исходную

                for (char ch : digit.toCharArray()) {
                    if((int)ch - 87  >= system){
                        flag = false;
                        result.setText("Неверный символ или система счисления");
                    }
                }

                if (flag){
                    number = Long.parseLong(digit, system);
                    String new_number = Long.toString(number, new_system);
                    result.setText(new_number.toUpperCase(Locale.ROOT));
                }
            }
            else{
                result.setText("Ошибка");
                input_system.setText("");
                input_new_system.setText("");
            }
        }
    }

    public void ClearArea(View view){
        input_system.setText("");
        input_new_system.setText("");
        input_digit.setText("");
    }

    public void CloseActivity(View view){
        finish();
    }
}