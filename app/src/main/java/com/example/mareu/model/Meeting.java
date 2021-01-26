package com.example.mareu.model;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /** Identifier */
    private long id;

    /** Date */
    private String date;

    /** Place */
    private String place;

    /** Participants */
    private String participants;

    /** Hour */
    private String hour;

    /** Subject */
    private String subject;

    /**
     * Constructor
     * @param id long
     * @param date String
     * @param place String
     * @param hour String
     * @param participants String
     * @param subject String
     */
    public Meeting(long id, String date, String place, String participants, String hour, String subject){

        this.id = id;
        this.date = date;
        this.place = place;
        this.participants = participants;
        this.hour = hour;
        this.subject = subject;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
}
