package com.example.franfirmino.taskyapplication.ScheduleMessage;

/**
 * Created by Fran Firmino on 04/04/2017.
 */

public class Contact {

    String name, phone;

    public Contact(){

    }

    public Contact(String name, String phone){
        this.name  = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
