package com.example.franfirmino.taskyapplication.Events;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.franfirmino.taskyapplication.BaseActivity;
import com.example.franfirmino.taskyapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListEvents extends AppCompatActivity {

    private ArrayList<Event> eventList;
    private ListView listView;
    private FloatingActionButton addNew;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        listView = (ListView) findViewById(R.id.listView);
        addNew =(FloatingActionButton) findViewById(R.id.addNew);


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEvents.this, EventActivity.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();

        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/data/events/");

        Query queryRef = mFirebaseDatabaseReference.orderByKey();
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                    Map<String, Object> objectMap = (HashMap<String, Object>)
                            dataSnapshot.getValue();
                    eventList = new ArrayList<Event>();

                    for (Object obj : objectMap.values()) {
                        if (obj instanceof Map) {
                            Map<String, Object> mapObj = (Map<String, Object>) obj;
                            Event event = new Event();
                            event.setTitle((String) mapObj.get("title"));
                            event.setEventDate((String) mapObj.get("date"));
                            event.setTime((String) mapObj.get("time"));
                            event.setDescription((String) mapObj.get("description"));
                            event.setPic((String) mapObj.get("pic"));

                            eventList.add(event);

                        }
                    }

                    adapter = new CustomAdapter(eventList, getApplicationContext());

                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Event event = eventList.get(position);

                            String cTitle = event.getTitle();
                            String cPic = event.getPic();
                            String cDate = event.getEventDate();
                            String cTime = event.getTime();
                            String cDes = event.getDescription();

                            Intent intent = new Intent(ListEvents.this, eventProfile.class);
                            intent.putExtra("Title", cTitle);
                            intent.putExtra("Date", cDate);
                            intent.putExtra("Time", cTime);
                            intent.putExtra("Des", cDes);
                            intent.putExtra("Pic", cPic);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
