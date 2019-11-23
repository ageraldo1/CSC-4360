package com.gsu.csc.crud;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class details extends AppCompatActivity {

    private TextView txtID;
    private TextView txtName;
    private TextView txtSex;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null ) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        txtID = (TextView) findViewById(R.id.txtID);
        txtName = (TextView) findViewById(R.id.txtName);
        txtSex = (TextView) findViewById(R.id.txtSex);

        extras = getIntent().getExtras();

        if ( extras != null ){

            txtID.setText(String.valueOf(extras.getInt("id")));
            txtName.setText(extras.getString("name"));
            txtSex.setText(extras.getString("sex"));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
