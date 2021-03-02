package com.example.mareu.model;

import android.graphics.Color;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /**
     * Identifier
     */
    private long id;

    /**
     * Date
     */
    private String date;

    /**
     * Place
     */
    private String room;

    /**
     * Participants
     */
    private String participants;

    /**
     * Hour
     */
    private String hour;

    /**
     * Subject
     */
    private String subject;

    /**
     * Color
     */
    private int color;

    /**
     * Constructor
     *
     * @param id           long
     * @param date         String
     * @param room        String
     * @param hour         String
     * @param participants String
     * @param subject      String
     * @param colorHex     String
     */
    public Meeting(long id, String date, String room, String participants, String hour, String subject, String colorHex) {

        this.id = id;
        this.date = date;
        this.room = room;
        this.participants = participants;
        this.hour = hour;
        this.subject = subject;
        this.color = Color.parseColor(colorHex);
    }

    /**
     * Constructor
     *
     * @param id           long
     * @param date         String
     * @param room        String
     * @param hour         String
     * @param participants String
     * @param subject      String
     * @param color        int
     */
    public Meeting(long id, String date, String room, String participants, String hour, String subject, int color) {

        this.id = id;
        this.date = date;
        this.room = room;
        this.participants = participants;
        this.hour = hour;
        this.subject = subject;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
