package com.example.plottest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Get_Function extends AppCompatActivity {

    Button go_to_graph;

    public void startNewActivity() {
        EditText editText = findViewById(R.id.func);
        Intent intent = new Intent(this, Graphic.class);
        intent.putExtra("func", editText.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_function);
        go_to_graph = findViewById(R.id.GoToGraphics);
        go_to_graph.setOnClickListener(this::onClickToGraph);
    }
    private void onClickToGraph(View view){startNewActivity();}

    public void CloseActivity(View view){
        finish();
    }
}