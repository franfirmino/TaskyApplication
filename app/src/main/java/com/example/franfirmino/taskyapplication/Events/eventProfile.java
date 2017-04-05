package com.example.franfirmino.taskyapplication.Events;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.franfirmino.taskyapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class eventProfile extends AppCompatActivity {

    private String title, date, time, pic, desc;
    private TextView eventName, dateEvent, timeEvent, descEvent;
    private ImageView imageEvent;
    private Button removeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_profile);

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        date = intent.getStringExtra("Date");
        time = intent.getStringExtra("Time");
        desc = intent.getStringExtra("Des");
        pic = intent.getStringExtra("Pic");

        eventName = (TextView) findViewById(R.id.eventName);
        dateEvent = (TextView) findViewById(R.id.dateEvent);
        timeEvent = (TextView) findViewById(R.id.timeEvent);
        descEvent = (TextView) findViewById(R.id.descEvent);

        imageEvent = (ImageView) findViewById(R.id.imageEvent);
        removeBtn = (Button) findViewById(R.id.removeBtn);

        eventName.setText(title);
        dateEvent.setText(date);
        timeEvent.setText(time);
        descEvent.setText(desc);

        // Load Event Image
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        storageRef.child(pic).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri u) {
                Log.e("Got the URL", ""+ u);
                // Load the image using Glide
                Glide.with(getApplicationContext())
                        .load(u)
                        .into(imageEvent);

                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("ERROR", ""+ exception);
                // Handle any errors
            }
        });


   removeBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           FirebaseDatabase database = FirebaseDatabase.getInstance();
           DatabaseReference myRef = database.getReference("/data/");

           Query removeQuery = myRef.child("events").orderByChild("title").equalTo(title);

           removeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                       appleSnapshot.getRef().removeValue();

                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {
                   Log.d("onCancelled", String.valueOf(databaseError.toException()));
               }
           });

           Intent intent = new Intent(eventProfile.this, ListEvents.class);
           startActivity(intent);


       }
   });



    }
}
