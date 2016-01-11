package com.vendolink.stopgambling;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;


public class SettingsActivity extends ActionBarActivity implements ItemPickerDialogFragment.OnItemSelectedListener, ItemPickerDialogFragment.OnDismissListener{

    String year, month, day, hour, minute;

    ArrayList<ItemPickerDialogFragment.Item> howMuchItems = new ArrayList<>();
    ArrayList<ItemPickerDialogFragment.Item> howLongItems = new ArrayList<>();

    ItemPickerDialogFragment howMuchFragment = null;
    ItemPickerDialogFragment howLongFragment = null;

    @Override
    protected void onPause(){
        super.onPause();
        getCurrentFocus().clearFocus();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_1) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_1))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_2) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_2))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_3) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_3))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_4) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_4))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_5) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_5))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_6) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_6))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_7) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_7))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_8) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_8))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_9) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_9))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_10) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_10))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_11) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_11))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_12) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_12))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_13) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_13))));
        howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_14) + " " + getString(R.string.currency), parseInt(getString(R.string.item_picker_amount_14))));
        //howMuchItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_amount_15), parseInt(getString(R.string.item_picker_amount_15))));

        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_1) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_1))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_2) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_2))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_3) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_3))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_4) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_4))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_5) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_5))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_6) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_6))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_7) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_7))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_8) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_8))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_9) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_9))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_10) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_10))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_11) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_11))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_12) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_12))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_13) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_13))));
        howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_14) + " " + getString(R.string.hours_abbr), parseInt(getString(R.string.item_picker_time_14))));
        //howLongItems.add(new ItemPickerDialogFragment.Item(getString(R.string.item_picker_time_15), parseInt(getString(R.string.item_picker_time_15))));

        SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
        String reason = settings.getString("settings_reason", "");
        int amount = settings.getInt("settings_amount", -1);
        int time = settings.getInt("settings_time", -1);
        String last = settings.getString("settings_last", "");

        EditText whyStop = (EditText) findViewById(R.id.why_stop_input);
        EditText howMuch = (EditText) findViewById(R.id.how_much_input);
        EditText howLong = (EditText) findViewById(R.id.how_long_input);
        EditText lastPlayed = (EditText) findViewById(R.id.last_played_input);

        whyStop.setText(reason);
        howMuch.setText(amount == -1 ? "" : "" + amount + " " + getString(R.string.currency));
        howLong.setText(time == -1 ? "" : "" + time + " " + getString(R.string.hours_abbr));
        lastPlayed.setText(last);

        howMuchFragment = ItemPickerDialogFragment.newInstance(
                "Number Picker",
                howMuchItems,
                getSelectedIndex(howMuch, "item_picker_amount_")
        );

        howLongFragment = ItemPickerDialogFragment.newInstance(
                "Number Picker",
                howLongItems,
                getSelectedIndex(howLong, "item_picker_time_")
        );

        lastPlayed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment fragment = new DatePickerFragment();
                    fragment.show(getFragmentManager(), "datePicker");
                } else {
                    hideKeyboard();
                }
            }
        });

        howMuch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    howMuchFragment.show(getFragmentManager(), "ItemPicker");
                }
                hideKeyboard();
            }
        });

        howLong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    howLongFragment.show(getFragmentManager(), "ItemPicker");
                }
                hideKeyboard();
            }
        });

        Button next = (Button) findViewById(R.id.finish);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText whyStop = (EditText) findViewById(R.id.why_stop_input);
                String reason = whyStop.getText().toString();

                EditText howMuch = (EditText) findViewById(R.id.how_much_input);
                String amount = howMuch.getText().toString();

                EditText howLong = (EditText) findViewById(R.id.how_long_input);
                String time = howLong.getText().toString();

                EditText lastPlayed = (EditText) findViewById(R.id.last_played_input);
                String last = lastPlayed.getText().toString();

                if( reason.equals("") ){
                    Toast.makeText(getApplication(), "Fyll i varför du vill sluta spela", Toast.LENGTH_SHORT).show();
                    return;
                } else if( amount.equals("") ){
                    Toast.makeText(getApplication(), "Fyll i hur mycket du spelar", Toast.LENGTH_SHORT).show();
                    return;
                } else if( time.equals("") ){
                    Toast.makeText(getApplication(), "Fyll i lång tid du spelar per vecka", Toast.LENGTH_SHORT).show();
                    return;
                } else if( last.equals("") ){
                    Toast.makeText(getApplication(), "Fyll i när du senast spelade", Toast.LENGTH_SHORT).show();
                    return;
                }

                amount = amount == "" ? "0" : amount.replaceAll("\\D", "");
                time = time == "" ? "0" : time.replaceAll("\\D", "");

                SharedPreferences settings = getSharedPreferences(getString(R.string.storagekey), 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("settings_reason", reason);
                editor.putInt("settings_amount", parseInt(amount));
                editor.putInt("settings_time", parseInt(time));
                editor.putString("settings_last", last);

                editor.putBoolean("first_launch", false);
                editor.commit();

                Intent startIntent = new Intent(getPackageName() + ".START_SCHEDULING");
                sendBroadcast(startIntent);

                if(!ProgressActivity.started) {
                    Intent progress = new Intent(SettingsActivity.this, ProgressActivity.class);
                    SettingsActivity.this.startActivity(progress);
                } else {
                    finish();
                }
            }
        });
    }

    private int getSelectedIndex(EditText editText, String identifier) {
        String text = editText.getText().toString().replaceAll("\\D", "");
        for(int i = 1; i <= 14; ++i){
            int stringId = getApplicationContext().getResources().getIdentifier(identifier + i, "string", getApplicationContext().getPackageName());
            if(text.equals(getString(stringId))){
                return i-1;
            }
        }
        return -1;
    }

    @Override
    public void onItemSelected(ItemPickerDialogFragment fragment, ItemPickerDialogFragment.Item item, int index) {
        final EditText current = (EditText) getCurrentFocus();
        final ItemPickerDialogFragment.Item item1 = item;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(current.getId() == R.id.how_much_input)
                    current.setText("" + item1.getIntValue() + " " + getString(R.string.currency));
                if(current.getId() == R.id.how_long_input)
                    current.setText("" + item1.getIntValue() + " " + getString(R.string.hours_abbr));
            }
        });
    }

    @Override
    public void onDismiss(ItemPickerDialogFragment fragment) {
        Log.d("LOGGING", "dismissed");
        getCurrentFocus().clearFocus();/*
        TextView tv = (TextView) getCurrentFocus().findViewById(R.id.why_stop_label);
        tv.requestFocus();*/
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener, DatePickerDialog.OnDismissListener {

        Boolean dateSet = false;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
            Date d = null;
            try {
                d = format.parse(((EditText) getActivity().getCurrentFocus()).getText().toString());
            } catch(Exception e){
                d = new Date();
            }

            final Calendar c = Calendar.getInstance();
            c.setTime(d);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String datePicked = year + "-" + ((month+1) < 10 ? "0" + (month+1) : (month+1)) + "-" + (day < 10 ? "0" + day : day);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = sdf.parse(datePicked);
            } catch (ParseException e){
                Log.d("ERROR:", "PARSE EXCEPTION");
            }
            java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity().getApplicationContext());
            EditText lastPlayed = (EditText) getActivity().findViewById(R.id.last_played_input);
            lastPlayed.setText(dateFormat.format(date));

            lastPlayed.setText(datePicked);

            DialogFragment fragment = new TimePickerFragment();
            fragment.show(getFragmentManager(), "timePicker");

            dateSet = true;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if(!dateSet) {
                TextView tv = (TextView) getActivity().findViewById(R.id.why_stop_label);
                tv.requestFocus();
            }
            dateSet = false;
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener, TimePickerDialog.OnDismissListener{

        Boolean timeSet = false;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hour, int minute) {
            String time = (hour < 10 ? "0" + hour: hour) + ":" + (minute < 10 ? "0" + minute: minute);
            EditText lastPlayed = (EditText) getActivity().findViewById(R.id.last_played_input);
            lastPlayed.setText(lastPlayed.getText() + " " + time);

            TextView tv = (TextView) getActivity().findViewById(R.id.why_stop_label);
            tv.requestFocus();

            timeSet = true;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if(!timeSet) {
                EditText lastPlayed = (EditText) getActivity().findViewById(R.id.last_played_input);
                lastPlayed.setText(lastPlayed.getText() + " 00:00");

                TextView tv = (TextView) getActivity().findViewById(R.id.why_stop_label);
                tv.requestFocus();
            }
            timeSet = false;
        }
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
