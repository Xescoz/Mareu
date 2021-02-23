package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {
    /**
     * Get all my Meetings
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Create a meeting
     *
     * @param meeting meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Delete a meeting
     *
     * @param meeting meeting
     */
    void deleteMeeting(Meeting meeting);
    /**
     * Return meetings matching the date
     *@param dateToFilter dateToFilter
     * @return {@link List}
     */
    List<Meeting> returnMatchingMeetingsWithDate(String dateToFilter);
    /**
     * Return meetings matching the room
     *@param room room
     * @return {@link List}
     */
    List<Meeting> returnMatchingMeetingsWithRoom(String room);
}
