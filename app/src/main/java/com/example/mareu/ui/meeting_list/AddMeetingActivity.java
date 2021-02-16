package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.pickers.DatePickerFragment;
import com.example.mareu.pickers.TimePickerFragment;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddMeetingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private MeetingApiService mApiService;
    private ActivityAddMeetingBinding binding;
    private final List<String> participantsList = new ArrayList<>();
    final Calendar c = Calendar.getInstance(Locale.getDefault());
    private final int calendarYear = c.get(Calendar.YEAR);
    private int calendarMonth = c.get(Calendar.MONTH);
    private final int calendarDay = c.get(Calendar.DAY_OF_MONTH);
    private final int calendarHour = c.get(Calendar.HOUR_OF_DAY);
    private final int calendarMinute = c.get(Calendar.MINUTE);
    private int colorResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();

        init();
        initListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBinding(){
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void init() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.placeSpinner.setAdapter(adapter);

        calendarMonth++;
        binding.hour.setText(getString(R.string.hourPicker, intToString(calendarHour), intToString(calendarMinute)));
        binding.date.setText(getString(R.string.datePicker, intToString(calendarDay), intToString(calendarMonth), calendarYear));
        colorResult = R.color.colorAccent;
    }

    private void initListener(){
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(AddMeetingActivity.this);
                colorPicker.show();
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position,int color) {
                        binding.avatar.setColorFilter(color);
                        colorResult = color;
                    }

                    @Override
                    public void onCancel(){
                    }
                });
            }
        });

        binding.hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        binding.addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String participantName = binding.participantsLyt.getEditText().getText().toString();

                InputMethodManager inputManager = (InputMethodManager) AddMeetingActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(AddMeetingActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                if(isValidEmail(participantName)) {

                    Chip participantChip = new Chip(AddMeetingActivity.this);
                    participantChip.setText(participantName);
                    participantChip.setCloseIconVisible(true);

                    participantsList.add(participantName);

                    participantChip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.participantsChipGroup.removeView(v);
                            participantsList.remove(participantName);
                        }
                    });

                    binding.participantsChipGroup.addView(participantChip);
                }
                else{
                    Toast.makeText(AddMeetingActivity.this, R.string.email_address_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (participantsList.isEmpty())
                    Toast.makeText(AddMeetingActivity.this, R.string.list_is_empty, Toast.LENGTH_SHORT).show();
                else {
                    Meeting meeting = new Meeting(
                            System.currentTimeMillis(),
                            binding.dateLyt.getEditText().getText().toString(),
                            binding.placeSpinner.getSelectedItem().toString(),
                            createParticipantsString(),
                            binding.hourLyt.getEditText().getText().toString(),
                            binding.subjectLyt.getEditText().getText().toString(),
                            colorResult
                    );
                    mApiService.createMeeting(meeting);
                    finish();
                }
            }
        });
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onTimeSet(TimePicker view, int hourOfDayData, int minuteData) {
        String hourOfDay;
        String minute;

        hourOfDay = intToString(hourOfDayData);
        minute = intToString(minuteData);

        binding.hour.setText(getString(R.string.hourPicker, hourOfDay, minute));
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDateSet(DatePicker view, int year, int monthData, int dayData) {
        String month;
        String day;
        monthData++;

        month = intToString(monthData);
        day = intToString(dayData);

        binding.date.setText(getString(R.string.datePicker, day, month, year));
    }

    public String intToString(int intToChange){
        return (intToChange < 10) ? "0"+intToChange : ""+intToChange;
    }

    public String createParticipantsString (){
        String participantStringToReturn = "";

        for(int i = 0 ; i<participantsList.size(); i++ ){
            participantStringToReturn = (i == 0) ? participantsList.get(i) : participantStringToReturn + ", " + participantsList.get(i);
        }
        return participantStringToReturn;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}