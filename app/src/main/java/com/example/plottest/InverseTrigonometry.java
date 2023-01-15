package com.example.plottest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InverseTrigonometry extends AppCompatActivity {

    Button aSinBtn;
    Button aCosBtn;
    Button aTanBtn;
    Button aCtgBtn;

    TextView angle;
    EditText valueText;

    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() { ch = (++pos < str.length()) ? str.charAt(pos) : -1; }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Неожиданный символ: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) {
                        x /= parseFactor();
                    }
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Отсутствует ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Отсутствует ')' для " + func);
                    } else {
                        x = parseFactor();
                    }
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(x);
                            break;
                        case "cos":
                            x = Math.cos(x);
                            break;
                        case "tan":
                            x = Math.tan(x);
                            break;
                        case "log":
                            x = Math.log(x);
                            break;
                        case "abs":
                            x = Math.abs(x);
                            break;
                        default:
                            throw new RuntimeException("Неизвестная функция: " + func);
                    }
                } else { throw new RuntimeException("Неожиданный символ: " + (char)ch); }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverse_trigonometry);

        aSinBtn = findViewById(R.id.arcsin_btn);
        aSinBtn.setOnClickListener(v -> CalculateAngle(0));

        aCosBtn = findViewById(R.id.arccos_btn);
        aCosBtn.setOnClickListener(v -> CalculateAngle(1));

        aTanBtn = findViewById(R.id.arctg_btn);
        aTanBtn.setOnClickListener(v -> CalculateAngle(2));

        aCtgBtn = findViewById(R.id.arcctg_btn);
        aCtgBtn.setOnClickListener(v -> CalculateAngle(3));
    }

    public void CalculateAngle(int function) {
        valueText = findViewById(R.id.value);
        angle = findViewById(R.id.angle);
        double degree;
        double value;

        if (valueText.getText().toString().isEmpty())
            ShowWarning("Введите значение");
        else {
            try {
                value = eval(valueText.getText().toString());
            } catch (RuntimeException e) {
                ShowWarning(e.getMessage());
                return;
            }
            switch (function) {
                case 0: {
                    double aSin = Math.asin(value);
                    degree = 180 * aSin / Math.PI;
                    angle.setText(String.format("%.1f", degree));
                    break;
                }
                case 1: {
                    double aCos = Math.acos(value);
                    degree = 180 * aCos / Math.PI;
                    angle.setText(String.format("%.1f", degree));
                    break;
                }
                case 2: {
                    double aTan = Math.atan(value);
                    degree = 180 * aTan / Math.PI;
                    angle.setText(String.format("%.1f", degree));
                    break;
                }
                case 3: {
                    double aCtg = Math.atan(1.0 / value);
                    degree = 180 * aCtg / Math.PI;
                    angle.setText(String.format("%.1f", degree));
                    break;
                }
            }
        }
    }

    public void CloseActivity(View view){
        finish();
    }

    public void ShowWarning(String message) {
        AlertDialog.Builder warningBtn = new AlertDialog.Builder(InverseTrigonometry.this);
        warningBtn.setMessage(message).setPositiveButton("ОК", (dialog, which) -> dialog.cancel());

        AlertDialog NegNumOfRounds = warningBtn.create();
        NegNumOfRounds.show();
    }

    public void GotoMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}