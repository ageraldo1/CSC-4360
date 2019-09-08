package com.gsu.csc.sayhello;

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

    public void printHello(View v) {
        Button btn = (Button) v;
        btn.setText("You clicked me");

        yourName = findViewById(R.id.inputText);
        outputName = findViewById(R.id.outputText);


        outputName.setText("Hello, " + yourName.getText() );
        outputName.setVisibility(View.VISIBLE);
    }

}
