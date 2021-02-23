package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<Meeting> meetingsMatchingRoom = new ArrayList<>();
    private List<Meeting> meetingsMatchingDate = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
        meetingsMatchingRoom.remove(meeting);
        meetingsMatchingDate.remove(meeting);
    }

    @Override
    public List<Meeting> returnMatchingMeetingsWithDate(String dateToFilter){
        meetingsMatchingDate = new ArrayList<>();
        for(int i = 0 ; i<meetings.size(); i++ ){
            if (meetings.get(i).getDate().equals(dateToFilter) && !meetingsMatchingDate.contains(meetings.get(i)))
                meetingsMatchingDate.add(meetings.get(i));
        }
        return meetingsMatchingDate;
    }

    @Override
    public List<Meeting> returnMatchingMeetingsWithRoom(String room){
        meetingsMatchingRoom = new ArrayList<>();
        for(int i = 0 ; i<meetings.size(); i++ ){
            if (meetings.get(i).getPlace().equals(room) && !meetingsMatchingRoom.contains(meetings.get(i)))
                meetingsMatchingRoom.add(meetings.get(i));
        }
        return meetingsMatchingRoom;
    }
}
