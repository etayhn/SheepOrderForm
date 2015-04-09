package com.sheep.etayhn.sheeporderform;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Button selectFoodButton;

    private MenuItem makeOrderMenuItem;

    public static final int MAX_SHEEP_NUM_DIGITS = 2;
    public static final int MAX_SHEEP_VALUE = ((int) Math.pow(10, MAX_SHEEP_NUM_DIGITS)) - 1; // exclusive

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WHERE", "onCreate");
        setContentView(R.layout.activity_sheep_order_form);

        makeOrderButton = (Button) findViewById(R.id.b_order);
        makeOrderButton.setOnClickListener(this);

        selectFoodButton = (Button) findViewById(R.id.b_select);
        selectFoodButton.setOnClickListener(this);

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

    /* ********* Menu ********* */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("WHERE", "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sheep_order_form, menu);
        makeOrderMenuItem = menu.findItem(R.id.mi_make_order);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("WHERE", "onOptionsItemSelected");
        if (item.getItemId() == R.id.mi_make_order) {
            makeOrder();
        }
        return true;
    }

    /* ********* BUTTON ********* */

    private boolean getMakeOrderEnablingState() {

        Log.d("WHERE", "getMakeOrderEnablingState");

        // getting the value from the EditText
        String stringValue = numSheepEditText.getText().toString();
        int numSheepValue;
        numSheepValue = Integer.parseInt(stringValue);

        // getting the CheckBox state
        boolean isWithFoodChecked = withFoodCheckBox.isChecked();

        // enabling the button iff numSheep>0 and the checkbox is checked
        return (isWithFoodChecked && numSheepValue > 0);
    }

    public void updateMakeOrderEnabled() {
        Log.d("WHERE", "updateMakeOrderEnabled");

        boolean desiredState = getMakeOrderEnablingState();

        makeOrderButton.setEnabled(desiredState);

        if (makeOrderMenuItem != null) {
            makeOrderMenuItem.setEnabled(desiredState);
        }


    }

    @Override
    public void onClick(View v) {
        Log.d("WHERE", "onClick");
        if (v.getId() == R.id.b_order) {
            makeOrder();
        } else if (v.getId() == R.id.b_select) {
//            Intent orderSentIntent = new Intent(getApplicationContext(), order_sent.class);
//            startActivityForResult(orderSentIntent);
        }
    }

    public void makeOrder() {
        Log.d("WHERE", "makeOrder");
        Toast.makeText(getApplicationContext(), R.string.your_order_has_been_sent, Toast.LENGTH_SHORT).show();
    }

    /* ********* EDIT TEXT ********* */

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("WHERE", "onTextChanged");
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
        updateMakeOrderEnabled();

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
        Log.d("WHERE", "onProgressChanged");

        // getting the SeekBar value
        int numSheepValue = seekBar.getProgress();

        // changing the EditText according to the SeekBar value
        numSheepEditText.setText("" + numSheepValue);

        // enabling or disabling the button
        updateMakeOrderEnabled();
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
        Log.d("WHERE", "onCheckedChanged");
        // enabling or disabling the 'select' button
        if (isChecked) {
            selectFoodButton.setEnabled(true);
        } else {
            selectFoodButton.setEnabled(false);
        }
        // enabling or disabling the 'make order' buttons
        updateMakeOrderEnabled();
    }
}
