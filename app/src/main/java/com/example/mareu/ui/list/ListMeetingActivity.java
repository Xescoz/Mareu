package com.example.mareu.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.global.Utils;
import com.example.mareu.model.Meeting;
import com.example.mareu.pickers.DatePickerFragment;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.add.AddMeetingActivity;

import java.util.List;

public class ListMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private MeetingRecyclerViewAdapter adapter;
    private com.example.mareu.databinding.ActivityListMeetingBinding binding;
    MeetingApiService apiService = DI.getMeetingApiService();
    List<Meeting> meetings = apiService.getMeetings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBinding();

        initRecyclerView(meetings);

        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView(meetings);
        adapter.notifyDataSetChanged();
    }

    private void initBinding() {
        binding = com.example.mareu.databinding.ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void initRecyclerView(List<Meeting> meetings) {
        adapter = new MeetingRecyclerViewAdapter(meetings, this.getApplicationContext());
        binding.meetingRecyclerView.setAdapter(adapter);
        binding.meetingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMeetingActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.dateFilter:
                showDatePickerDialog();
                return true;
            case R.id.roomA:
                showRoom("Reunion A");
                return true;
            case R.id.roomB:
                showRoom("Reunion B");
                return true;
            case R.id.roomC:
                showRoom("Reunion C");
                return true;
            case R.id.roomD:
                showRoom("Reunion D");
                return true;
            case R.id.roomE:
                showRoom("Reunion E");
                return true;
            case R.id.roomF:
                showRoom("Reunion F");
                return true;
            case R.id.roomG:
                showRoom("Reunion G");
                return true;
            case R.id.roomH:
                showRoom("Reunion H");
                return true;
            case R.id.roomI:
                showRoom("Reunion I");
                return true;
            case R.id.roomJ:
                showRoom("Reunion J");
                return true;
            case R.id.cancel:
                initRecyclerView(meetings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showRoom(String room){
        List<Meeting> meetingsMatchingRoom = apiService.returnMatchingMeetingsWithRoom(room);
        initRecyclerView(meetingsMatchingRoom);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthData, int dayData) {
        String month;
        String day;
        monthData++;

        month = Utils.intToString(monthData);
        day = Utils.intToString(dayData);
        String dateToFilter = getString(R.string.date_picker, day, month, year);

        List<Meeting> meetingsMatchingDate = apiService.returnMatchingMeetingsWithDate(dateToFilter);
        initRecyclerView(meetingsMatchingDate);
    }
}
