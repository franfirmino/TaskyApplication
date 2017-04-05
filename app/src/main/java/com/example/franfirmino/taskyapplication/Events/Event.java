package com.example.franfirmino.taskyapplication.Events;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fran Firmino on 04/04/2017.
 */

public class Event {

        public Event(){

    }

        public String title, description, pic, eventDate, time;

        public Event(String eventDate, String time, String title, String description, String pic){
            this.eventDate = eventDate;
            this.time = time;
            this.title = title;
            this.description = description;
            this.pic = pic;

        }


        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("date", eventDate);
            result.put("time", time);
            result.put("title", title);
            result.put("description", description);
            result.put("pic", pic);
            return result;
        }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
