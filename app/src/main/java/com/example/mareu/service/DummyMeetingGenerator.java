package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1,"10 juin 2021","Reunion A","ben@live.com","14H","Mario"),
            new Meeting(2,"10 juin 2021","Reunion A","ben@live.com","14H","Mario")
    );

    static List<Meeting> generateMeetings(){
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
