package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

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

public class AddMeetingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private MeetingApiService mApiService;
    private ActivityAddMeetingBinding binding;


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
        binding.participantsLyt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.createButton.setEnabled(s.length() > 0);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.placeSpinner.setAdapter(adapter);

    }

    private void initListener(){
        binding.addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chip participantChip = new Chip(AddMeetingActivity.this);
                participantChip.setText(binding.participantsLyt.getEditText().getText().toString());
                participantChip.setCloseIconVisible(true);

                participantChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.participantsChipGroup.removeView(v);
                    }
                });
                binding.participantsChipGroup.addView(participantChip);
            }
        });
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = new Meeting(
                        System.currentTimeMillis(),
                        binding.dateLyt.getEditText().getText().toString(),
                        binding.placeSpinner.getSelectedItem().toString(),
                        binding.participantsLyt.getEditText().getText().toString(),
                        binding.hourLyt.getEditText().getText().toString(),
                        binding.subjectLyt.getEditText().getText().toString()
                );
                mApiService.createMeeting(meeting);
                finish();
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
        String stringToReturn;

        if(intToChange < 10){
            stringToReturn = "0"+intToChange;
        }
        else
            stringToReturn = ""+intToChange;

        return stringToReturn;
    }

}