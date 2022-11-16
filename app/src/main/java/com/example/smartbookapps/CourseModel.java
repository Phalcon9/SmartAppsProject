package com.example.smartbookapps;

public class CourseModel {
    String CourseName, Venue,Time, id, Deparment;
    int count = 0;

    public CourseModel() {
    }

    public CourseModel(String courseName, String venue, String time , String deparment, String id) {
        CourseName = courseName;
        Venue = venue;
        Time = time;
        Deparment = deparment;
        this.id = id;
    }

    public String getDeparment() {
        return Deparment;
    }

    public void setDeparment(String deparment) {
        Deparment = deparment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
