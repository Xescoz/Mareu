package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import java.util.List;

public class ListMeetingActivity extends AppCompatActivity {

    private MeetingApiService mApiService;
    private MeetingRecyclerViewAdapter adapter;
    private com.example.mareu.databinding.ActivityListMeetingBinding binding;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        mApiService = DI.getMeetingApiService();
        List<Meeting> meetings = mApiService.getMeetings();

        adapter = new MeetingRecyclerViewAdapter(meetings, this.getApplicationContext());
        binding.meetingList.setAdapter(adapter);
        binding.meetingList.setLayoutManager(new LinearLayoutManager(this));

        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initBinding(){
        binding = com.example.mareu.databinding.ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void initListener(){
        binding.addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMeetingActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

}