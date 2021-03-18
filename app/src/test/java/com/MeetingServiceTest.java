package com;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.DummyMeetingGenerator;
import com.example.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertEquals( expectedMeetings, meetings);
    }

    @Test
    public void getMeetingMatchingRoomWithSuccess(){
        List<Meeting> meetingsMatchingRoom = service.returnMatchingMeetingsWithRoom("Salle B");
        assertEquals("Salle B", meetingsMatchingRoom.get(0).getRoom());
    }

    @Test
    public void getMeetingMatchingDateWithSuccess(){
        List<Meeting> meetingsMatchingDate = service.returnMatchingMeetingsWithDate("10/02/2021");
        assertEquals("10/02/2021", meetingsMatchingDate.get(0).getDate());
    }

    @Test
    public void deleteMeetingWithSuccess(){
        Meeting meetingToDelete = service.getMeetings().get(1);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting meetingToAdd = new Meeting(4,"test","test","test","0102","test","test");
        service.createMeeting(meetingToAdd);
        assertTrue(service.getMeetings().contains(meetingToAdd));
    }

}