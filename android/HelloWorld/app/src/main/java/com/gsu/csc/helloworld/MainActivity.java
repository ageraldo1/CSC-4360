package com.gsu.csc.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private EditText yourName;
    private TextView outputName;
    private Button button;

    public void printHello(View v) {

        yourName = findViewById(R.id.inputText);
        outputName = findViewById(R.id.outputLabel);
        button = findViewById(R.id.bntGoodbye);
        button.setText("Goodbye");

        Button btn = (Button) v;
        btn.setText("You clicked me");

        outputName.setText("Hello, " + yourName.getText() );
        outputName.setVisibility(View.VISIBLE);
    }

    public void printGoodbye(View v) {
        yourName = findViewById(R.id.inputText);
        outputName = findViewById(R.id.outputLabel);
        button = findViewById(R.id.bntHello);
        button.setText("Hello");

        Button btn = (Button) v;
        btn.setText("You clicked me");

        outputName.setText("Goodbye, " + yourName.getText() );
        outputName.setVisibility(View.VISIBLE);
    }


}
