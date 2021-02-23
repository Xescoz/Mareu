package com.example.mareu.ui.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.global.Utils;
import com.example.mareu.model.Meeting;
import com.example.mareu.pickers.DatePickerFragment;
import com.example.mareu.pickers.TimePickerFragment;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddMeetingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private MeetingApiService mApiService;
    private ActivityAddMeetingBinding binding;

    private final List<String> participantsList = new ArrayList<>();
    private int colorResult;

    private final Calendar calendar = Calendar.getInstance(Locale.getDefault());
    private final int calendarYear = calendar.get(Calendar.YEAR);
    private int calendarMonth = calendar.get(Calendar.MONTH);
    private final int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
    private final int calendarHour = calendar.get(Calendar.HOUR_OF_DAY);
    private final int calendarMinute = calendar.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        init();
        initListener();
    }

    private void initBinding() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void init() {
        initRoomSpinner();
        calendarMonth++;
        binding.hourEdit.setText(getString(R.string.hour_picker, Utils.intToString(calendarHour), Utils.intToString(calendarMinute)));
        binding.dateEdit.setText(getString(R.string.date_picker, Utils.intToString(calendarDay), Utils.intToString(calendarMonth), calendarYear));
        colorResult = R.color.colorAccent;
    }

    private void initRoomSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.roomSpinner.setAdapter(adapter);
    }

    private void initListener() {
        binding.avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRoomColor();
            }
        });

        binding.hourEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        binding.dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        binding.addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParticipants();
            }
        });

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMeeting();
            }
        });
    }

    private void setRoomColor() {
        ColorPicker colorPicker = new ColorPicker(AddMeetingActivity.this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                binding.avatarImage.setColorFilter(color);
                colorResult = color;
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void addParticipants() {
        String participantName = binding.participantsLayout.getEditText().getText().toString();

        Utils.hideKeyboard(AddMeetingActivity.this);

        if (Utils.isValidEmail(participantName)) {

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
        } else {
            Toast.makeText(AddMeetingActivity.this, R.string.email_address_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void createMeeting() {
        if (participantsList.isEmpty()) {
            Toast.makeText(AddMeetingActivity.this, R.string.list_is_empty, Toast.LENGTH_SHORT).show();
        } else {
            Meeting meeting = new Meeting(
                    System.currentTimeMillis(),
                    binding.dateInput.getEditText().getText().toString(),
                    binding.roomSpinner.getSelectedItem().toString(),
                    createParticipantsString(),
                    binding.hourInput.getEditText().getText().toString(),
                    binding.subjectInput.getEditText().getText().toString(),
                    colorResult
            );
            mApiService.createMeeting(meeting);
            finish();
        }
    }

    public String createParticipantsString() {
        String participantStringToReturn = "";
        for (int i = 0; i < participantsList.size(); i++) {
            participantStringToReturn = (i == 0) ? participantsList.get(i) : participantStringToReturn + ", " + participantsList.get(i);
        }
        if(participantStringToReturn.length() >= 30)
            participantStringToReturn = participantStringToReturn.substring(0,30)+"...";
        return participantStringToReturn;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDayData, int minuteData) {
        String hourOfDay;
        String minute;

        hourOfDay = Utils.intToString(hourOfDayData);
        minute = Utils.intToString(minuteData);

        binding.hourEdit.setText(getString(R.string.hour_picker, hourOfDay, minute));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthData, int dayData) {
        String month;
        String day;
        monthData++;

        month = Utils.intToString(monthData);
        day = Utils.intToString(dayData);

        binding.dateEdit.setText(getString(R.string.date_picker, day, month, year));
    }
}