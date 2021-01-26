package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddMeetingActivity extends AppCompatActivity {

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
        binding.placeLyt.getEditText().addTextChangedListener(new TextWatcher() {
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
    }

    private void initListener(){
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = new Meeting(
                        System.currentTimeMillis(),
                        binding.dateLyt.getEditText().getText().toString(),
                        binding.placeLyt.getEditText().getText().toString(),
                        binding.participantsLyt.getEditText().getText().toString(),
                        binding.hourLyt.getEditText().getText().toString(),
                        binding.subjectLyt.getEditText().getText().toString()
                );
                mApiService.createMeeting(meeting);
                finish();
            }
        });
    }

    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}