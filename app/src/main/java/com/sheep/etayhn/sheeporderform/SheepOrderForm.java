package com.sheep.etayhn.sheeporderform;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;


public class SheepOrderForm extends ActionBarActivity implements View.OnClickListener, TextWatcher, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private Button makeOrderButton;
    private SeekBar numSheepSeekBar;
    private CheckBox withFoodCheckBox;
    private EditText numSheepEditText;
    private int editTextPosition;

    public static final int MAX_SHEEP_NUM_DIGITS = 3;
    public static final int MAX_SHEEP_VALUE = ((int) Math.pow(10, MAX_SHEEP_NUM_DIGITS)) - 1; // exclusive

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheep_order_form);

        makeOrderButton = (Button) findViewById(R.id.b_order);
        makeOrderButton.setOnClickListener(this);

        numSheepEditText = (EditText) findViewById(R.id.et_numSheep);
        numSheepEditText.addTextChangedListener(this);

        numSheepSeekBar = (SeekBar) findViewById(R.id.sb_numSheep);
        numSheepSeekBar.setOnSeekBarChangeListener(this);

        withFoodCheckBox = (CheckBox) findViewById(R.id.cb_withFood);
        withFoodCheckBox.setOnCheckedChangeListener(this);

        // setting maximum sheep number in the seek bar
        numSheepSeekBar.setMax(MAX_SHEEP_VALUE);
        // setting maximum sheep number in the edit text
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(MAX_SHEEP_NUM_DIGITS);
        numSheepEditText.setFilters(filterArray);
    }

    /* ********* BUTTON ********* */

    public void updateButtonStateIfNecessary() {

        // getting the value from the EditText
        String stringValue = numSheepEditText.getText().toString();
        int numSheepValue;
        try {
            numSheepValue = Integer.parseInt(stringValue);
        } catch (NumberFormatException nfe) {
            numSheepValue = 0;
        }

        // getting the CheckBox state
        boolean isWithFoodChecked = withFoodCheckBox.isChecked();

        // enabling the button iff numSheep>0 and the checkbox is checked
        if (isWithFoodChecked && numSheepValue > 0) {
            makeOrderButton.setEnabled(true);
        } else {
            makeOrderButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b_order) {
            Toast.makeText(getApplicationContext(), R.string.your_order_has_been_sent, Toast.LENGTH_SHORT).show();
//            Intent orderSentIntent = new Intent(getApplicationContext(), order_sent.class);
//            startActivity(orderSentIntent);
        }
    }

    /* ********* EDIT TEXT ********* */

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int value;

        // retrieving the value
        try {
            value = Integer.parseInt(s.toString());
        } catch (NumberFormatException nfe) {
            value = 0;
        }

        // changing the SeekBar according to the EditText value
        numSheepSeekBar.setProgress(value);

        // enabling or disabling the button
        updateButtonStateIfNecessary();

        // putting the cursor in place
        int cursorPosition = start + count;
        if (cursorPosition > numSheepEditText.length()) {
            cursorPosition = numSheepEditText.length();
        }
        numSheepEditText.setSelection(cursorPosition);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /* ********* SEEK BAR ********* */

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // getting the SeekBar value
        int numSheepValue = seekBar.getProgress();

        // changing the EditText according to the SeekBar value
        numSheepEditText.setText("" + numSheepValue);

        // enabling or disabling the button
        updateButtonStateIfNecessary();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /* ********* CHECK BOX ********* */

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // enabling or disabling the button
        updateButtonStateIfNecessary();
    }
}
