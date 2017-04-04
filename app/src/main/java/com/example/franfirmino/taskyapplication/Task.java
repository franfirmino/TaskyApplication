package com.example.franfirmino.taskyapplication;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fran Firmino on 04/04/2017.
 */

public class Task {

        public Date eventDate, reminder;
        public String title, description;

        public Task() {
        }

        public Task(Date eventDate, String title, String description, Date reminder){
            this.eventDate = eventDate;
            this.title = title;
            this.description = description;
            this.reminder = reminder;
        }


        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("date", eventDate);
            result.put("event", title);
            result.put("description", description);
            result.put("reminder", reminder);
            return result;
        }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date reminder) {
        this.reminder = reminder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
