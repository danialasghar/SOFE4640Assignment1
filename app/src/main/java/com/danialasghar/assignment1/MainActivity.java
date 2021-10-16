package com.danialasghar.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    Spinner amortizationLength;
    TextInputEditText principleAmount;
    TextInputEditText interestRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create AppBar as header
        Toolbar myToolbar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(myToolbar);

        //Instantiate the Spinner component, populate values using array and sets default to 25 years
        amortizationLength = (Spinner) findViewById(R.id.amortizationPeriod);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.amortizationYears, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amortizationLength.setAdapter(adapter);
        amortizationLength.setSelection(23);

        //Instantiate the global variables with their correlating ids
        principleAmount = (TextInputEditText) findViewById(R.id.principleAmount);
        interestRate = (TextInputEditText) findViewById(R.id.interestAmount);

    }

    //Calculate the monthly mortgage rate using M = P[r(1 + r)^n]/[(1 + r)^n - 1]
    private double calculateMortgage(double principle, double interestRate, int amortization){
        double monthlyRate = ((interestRate/100)/12);
        int months = amortization*12;

        double top = monthlyRate*(Math.pow((1+monthlyRate), months));
        double bottom = Math.pow((1+monthlyRate), months)-1;
        double result =  principle*(top/bottom);

        //Convert double to BigDecimal to do rounding properly
        BigDecimal decValue = new  BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);

        return decValue.doubleValue();
    }

    //Click handler for the calculate button
    public void onClickHandler(View view) {

        //Get all the resources
        String interestValue = interestRate.getText().toString();
        String principleValue = principleAmount.getText().toString();
        TextInputLayout interestLayout = (TextInputLayout) findViewById(R.id.interest);
        TextInputLayout principleLayout = (TextInputLayout) findViewById(R.id.principle);

        //Clears error messages for text input fields
        interestLayout.setError(null);
        principleLayout.setError(null);

        //Checks whether either of the input fields is empty, if so populate an error
        if(interestValue.trim().length() == 0 || principleValue.trim().length() == 0 ){
            if(interestValue.trim().length() == 0){
                interestLayout.setError("Enter a valid interest rate!");
            }
            if(principleValue.trim().length() == 0){
                principleLayout.setError("Enter a valid principle amount!");
            }
        }
        else{
            //Parse the values from all 3 fields
            double interest = Double.parseDouble(interestValue);
            double principle = Double.parseDouble(principleValue);
            int amortization = Integer.parseInt(amortizationLength.getSelectedItem().toString());

            //Calculate the mortgage rate
            double mortgageRate = calculateMortgage(principle, interest, amortization);

            //Pass the mortgage rate to second activity
            Bundle extras = new Bundle();
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            extras.putDouble("result", mortgageRate);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }
}