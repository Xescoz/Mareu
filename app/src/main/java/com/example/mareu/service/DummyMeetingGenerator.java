package com.example.mareu.service;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "10/06/2021", "Salle A", "ben@live.com", "14h00", "Reunion A", "#F84C44"),
            new Meeting(3, "10/02/2021", "Salle B", "ben@live.com", "16h30", "Reunion B", "#FFBB86FC"),
            new Meeting(2, "10/06/2021", "Salle C", "ben@live.com, alex@live.com", "14h30", "Reunion C", "#2262AF")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
