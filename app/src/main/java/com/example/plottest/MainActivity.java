package com.example.plottest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button calculator;
    Button graphics;
    Button trigonometry;
    Button guidebook;
    Button transfer;
    //Имена дочерних активити от главной страницые
    //добавь сюда имя страницы, если необходимо
    public Class[] set = {Calculator.class, Get_Function.class, Trigonometry.class, CC_activity.class, Guidebook.class};

    public void startCalculator(int i) {
        Intent intent = new Intent(this, set[i]);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Паша
        calculator = findViewById(R.id.Calculator);
        calculator.setOnClickListener(this::onClickCalculate);

        transfer = findViewById(R.id.c_transfer);
        transfer.setOnClickListener(this::onClickCC_activity);
        //Егор
        graphics = findViewById(R.id.GetGraphics);
        graphics.setOnClickListener(this::onClickGraphics);

        //Даня
        trigonometry = findViewById(R.id.trigonometry);
        trigonometry.setOnClickListener(this::onClickTrigonometry);

        guidebook = findViewById(R.id.GuideBook);
        guidebook.setOnClickListener(this::onClickGuidebook);
    }
    //Переход на другую страницу
    private void onClickCalculate(View view){startCalculator(0);}
    private void onClickGraphics(View view){startCalculator(1);}
    private void onClickTrigonometry(View view){startCalculator(2);}
    private void onClickCC_activity(View view){startCalculator(3);}
    private void onClickGuidebook(View view){startCalculator(4);}

}