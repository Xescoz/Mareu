package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {
    /**
     * Get all my Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Delete a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);
}
