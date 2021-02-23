package com.example.mareu.service;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "10/06/2021", "Reunion A", "ben@live.com", "14H00", "Mario", "#F84C44"),
            new Meeting(3, "10/02/2021", "Reunion B", "ben@live.com", "16H30", "Luigi", "#FFBB86FC"),
            new Meeting(2, "10/06/2021", "Reunion B", "ben@live.com, alex@live.com", "14H30", "Peach", "#2262AF")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
