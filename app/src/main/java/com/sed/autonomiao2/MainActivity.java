package com.sed.autonomiao2;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static final int BOMBOLINO_CAPACITY = 2;
    static final int BOMBOLINO_PRESSURE = 200;

    static final int BOMBOLA_CAPACITY = 10;
    static final int BOMBOLA_PRESSURE = 200;

    EditText capacityText;
    EditText pressureText;
    EditText consumptionText;
    TextView autonomyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        capacityText = (EditText) findViewById(R.id.capacity);
        pressureText = (EditText) findViewById(R.id.pressure);
        consumptionText = (EditText) findViewById(R.id.consumption);
        autonomyText = (TextView) findViewById(R.id.autonomia);

        Button calculateButton = (Button) findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int capacity = Integer.valueOf(capacityText.getText().toString());
//                int pressure = Integer.valueOf(pressureText.getText().toString());
//                int consumption = Integer.valueOf(consumptionText.getText().toString());
                calculateAutonomy();

//                autonomyText.setText(String.valueOf(autonomy));
            }
        });

        Button clearButton = (Button) findViewById(R.id.clear);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capacityText.setText("");
                pressureText.setText("");
                consumptionText.setText("");
                autonomyText.setText("...");
            }
        });

        Button capacityPlusB = (Button) findViewById(R.id.capacity_minus);
        Button capacityMinusB = (Button) findViewById(R.id.capacity_plus);
        Button pressurePlusB = (Button) findViewById(R.id.pressure_minus);
        Button pressureMinusB = (Button) findViewById(R.id.pressure_plus);
        Button consumptionPlusB = (Button) findViewById(R.id.consumption_minus);
        Button consumptionMinusB = (Button) findViewById(R.id.consumption_plus);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text;
                int value;
                int delta = 1;

                switch (v.getId()) {
                    case R.id.capacity_minus:
                        text = capacityText;
                        delta = -1;
                        break;
                    case R.id.capacity_plus:
                        text = capacityText;
                        break;
                    case R.id.pressure_minus:
                        text = pressureText;
                        delta = -1;
                        break;
                    case R.id.pressure_plus:
                        text = pressureText;
                        break;
                    case R.id.consumption_minus:
                        text = consumptionText;
                        delta = -1;
                        break;
                    case R.id.consumption_plus:
                        text = consumptionText;
                        break;
                    default:
                        text = capacityText;
                }
                String stringValue = text.getText().toString();
                if (stringValue.isEmpty())
                    value = 0 + delta;
                else
                    value = Integer.valueOf(stringValue) + delta;
                if (value < 0) value = 0;
                text.setText(String.valueOf(value));
                calculateAutonomy();
            }
        };
        capacityPlusB.setOnClickListener(onClickListener);
        capacityMinusB.setOnClickListener(onClickListener);
        pressurePlusB.setOnClickListener(onClickListener);
        pressureMinusB.setOnClickListener(onClickListener);
        consumptionPlusB.setOnClickListener(onClickListener);
        consumptionMinusB.setOnClickListener(onClickListener);

        Button bombola = (Button) findViewById(R.id.bombola);
        bombola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefault(BOMBOLA_CAPACITY, BOMBOLA_PRESSURE);
            }
        });
        Button bombolino = (Button) findViewById(R.id.bombolino);
        bombolino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefault(BOMBOLINO_CAPACITY, BOMBOLINO_PRESSURE);
            }
        });
        loadSpinner();

        ImageView myImage = (ImageView) findViewById(R.id.logo);
        myImage.setImageAlpha(80); //value: [0-255]. Where 0 is fully transparent and 255 is fully opaque.
    }

    private void loadSpinner() {
        /*
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bombole, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        */
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        switch(newConfig.orientation) {
//            case Configuration.ORIENTATION_LANDSCAPE:
//            case Configuration.ORIENTATION_PORTRAIT:
//                calculateAutonomy();
//                break;
//        }
//        super.onConfigurationChanged(newConfig);
//    }

    private void setDefault(int capacity, int pressure) {
        capacityText.setText(String.valueOf(capacity));
        pressureText.setText(String.valueOf(pressure));
        consumptionText.requestFocus();
    }

    private void calculateAutonomy() {

        int capacity = Integer.valueOf(capacityText.getText().toString().isEmpty() ? "0" :
                capacityText.getText().toString());
        int pressure = Integer.valueOf(pressureText.getText().toString().isEmpty() ? "0" :
                pressureText.getText().toString());
        int consumption = Integer.valueOf(consumptionText.getText().toString().isEmpty() ? "0" :
                consumptionText.getText().toString());

        autonomyText.setText(getString(R.string.autonomia_result, calculateAutonomy(capacity, pressure, consumption)));

    }

    private int calculateAutonomy(int capacity, int pressure, int consumption) {

        if (capacity == 0 | pressure == 0 | consumption == 0) return 0;

        int totOxigenAvailable = capacity * pressure;
        int autonomy = totOxigenAvailable / consumption;

        return autonomy;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                setDefault(BOMBOLINO_CAPACITY, BOMBOLINO_PRESSURE);
                break;
            case 2:
                setDefault(BOMBOLA_CAPACITY, BOMBOLA_PRESSURE);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
