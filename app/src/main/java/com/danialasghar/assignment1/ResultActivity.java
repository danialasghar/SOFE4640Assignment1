package com.danialasghar.assignment1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class ResultActivity extends AppCompatActivity {

    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Create AppBar as header
        Toolbar myToolbar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(myToolbar);

        //Create up button to go back to previous activity
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        //Intent to get the value passed from MainActivity
        Intent intent = getIntent();
        double mortgage;

        //Getting value from bundle
        Bundle extras = intent.getExtras();
        mortgage = extras.getDouble("result");

        //Write mortgage result to textview
        this.value = "Monthly Mortgage: \n" + "$" + Double.toString(mortgage);
        TextView result = (TextView) findViewById(R.id.result_view);
        result.setText(this.value);
    }

    //Click handler for the Fab icon
    public void shareClick(View view) {

        //Create an intent to share the mortgage result and email it
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("mailto:"));
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Mortgage");
        sendIntent.putExtra(Intent.EXTRA_TEXT, this.value);
        startActivity(sendIntent);
    }
}