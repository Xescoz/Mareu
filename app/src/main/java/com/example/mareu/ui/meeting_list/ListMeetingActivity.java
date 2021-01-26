package com.example.mareu.ui.meeting_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class ListMeetingActivity extends AppCompatActivity {

    private MeetingApiService mApiService;
    private MeetingRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);

        final FloatingActionButton addMeeting = findViewById(R.id.add_meeting);
        mApiService = DI.getMeetingApiService();

        RecyclerView rvMeetings = (RecyclerView) findViewById(R.id.meeting_list);
        List<Meeting> meetings = mApiService.getMeetings();

        adapter = new MeetingRecyclerViewAdapter(meetings, this);
        rvMeetings.setAdapter(adapter);
        rvMeetings.setLayoutManager(new LinearLayoutManager(this));

        initListener(addMeeting);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initListener(FloatingActionButton addMeeting){
        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMeetingActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }
}