package com.example.plottest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Trigonometry extends AppCompatActivity {

    Button sinBtn;
    Button cosBtn;
    Button tanBtn;
    Button ctgBtn;
    Button inverseTrigonometryBtn;

    TextView value;
    EditText angle;

    ImageView fraction;

    int[] fractions = {R.drawable.half, R.drawable.sqrt3by2, R.drawable.sqrt3by3,
            R.drawable.sqrt3, R.drawable.sqrt2by2,

            R.drawable.minus_half, R.drawable.minus_sqrt3by2, R.drawable.minus_sqrt3by3,
            R.drawable.minus_sqrt3, R.drawable.minus_sqrt2by2 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triganometry);
        Locale.setDefault(Locale.US);

        sinBtn = findViewById(R.id.sin_btn);
        sinBtn.setOnClickListener(v -> CalculateValue(0));

        cosBtn = findViewById(R.id.cos_btn);
        cosBtn.setOnClickListener(v -> CalculateValue(1));

        tanBtn = findViewById(R.id.tg_btn);
        tanBtn.setOnClickListener(v -> CalculateValue(2));

        ctgBtn = findViewById(R.id.ctg_btn);
        ctgBtn.setOnClickListener(v -> CalculateValue(3));

        inverseTrigonometryBtn = findViewById(R.id.inverseTrigonometry);
        inverseTrigonometryBtn.setOnClickListener(v -> StartInverseTrigonometry());
    }

    public void CloseActivity(View view){ finish(); }

    public void StartInverseTrigonometry() {
        Intent intent = new Intent(this, InverseTrigonometry.class);
        startActivity(intent);
    }

    public void CalculateValue(int function){
        value = findViewById(R.id.value);
        angle = findViewById(R.id.angle);

        if (angle.getText().toString().isEmpty())
            ShowWarning();
        else {
            int degree = Integer.parseInt(angle.getText().toString());
            double radian = Math.PI * degree / 180;

            switch (function) {
                case 0: {
                    double sin = Math.sin(radian);
                    SetImageOrText(sin);
                    break;
                }
                case 1: {
                    double cos = Math.cos(radian);
                    SetImageOrText(cos);
                    break;
                }
                case 2: {
                    double tan = Math.tan(radian);
                    if (tan > 58|| tan < -58) {
                        fraction.setImageResource(android.R.drawable.menuitem_background);
                        value.setText("-");
                    } else
                        SetImageOrText(tan);
                    break;
                    }
                case 3: {
                    double ctg = 1.0 / Math.tan(radian);
                    if (ctg > 58 || ctg < -58) {
                        fraction.setImageResource(android.R.drawable.menuitem_background);
                        value.setText("-");
                    } else
                        SetImageOrText(ctg);
                    break;
                }
            }
        }
    }

    public void SetImageOrText(double val) {
        fraction = findViewById(R.id.fraction);
        value.setText(" ");
        switch (String.format("%.3f", val)) {
            case "0.500": {
                fraction.setImageResource(fractions[0]);
                break;
            }
            case "0.866": {
                fraction.setImageResource(fractions[1]);
                break;
            }
            case "0.577": {
                fraction.setImageResource(fractions[2]);
                break;
            }
            case "1.732": {
                fraction.setImageResource(fractions[3]);
                break;
            }
            case "0.707": {
                fraction.setImageResource(fractions[4]);
                break;
            }
            case "-0.500": {
                fraction.setImageResource(fractions[5]);
                break;
            }
            case "-0.866": {
                fraction.setImageResource(fractions[6]);
                break;
            }
            case "-0.577": {
                fraction.setImageResource(fractions[7]);
                break;
            }
            case "-1.732": {
                fraction.setImageResource(fractions[8]);
                break;
            }
            case "-0.707": {
                fraction.setImageResource(fractions[9]);
                break;
            }
            default: {
                fraction.setImageResource(android.R.drawable.menuitem_background);
                value.setText(String.format("%.3f", val));
            }
        }
    }

    public void ShowWarning() {
        AlertDialog.Builder warningBtn = new AlertDialog.Builder(Trigonometry.this);
        warningBtn.setMessage("Введите значение угла").setPositiveButton("ОК", (dialog, which) -> dialog.cancel());

        AlertDialog NegNumOfRounds = warningBtn.create();
        NegNumOfRounds.show();
    }
}