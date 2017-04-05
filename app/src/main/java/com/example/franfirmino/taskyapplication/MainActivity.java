package com.example.franfirmino.taskyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.franfirmino.taskyapplication.Events.ListEvents;
import com.example.franfirmino.taskyapplication.ScheduleMessage.ContactsDisplayActivity;
import com.example.franfirmino.taskyapplication.ScheduleMessage.ScheduleMessage;


public class MainActivity extends AppCompatActivity {

    Button messageBtn, eventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageBtn = (Button) findViewById(R.id.messageBtn);
        eventBtn = (Button) findViewById(R.id.eventBtn);

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent messageIntent = new Intent(getApplicationContext(), ContactsDisplayActivity.class);
                startActivity(messageIntent);
            }
        });

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent messageIntent = new Intent(getApplicationContext(), ListEvents.class);
                startActivity(messageIntent);

            }
            });



    }
}
