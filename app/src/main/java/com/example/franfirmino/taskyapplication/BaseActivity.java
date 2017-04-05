package com.example.franfirmino.taskyapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.franfirmino.taskyapplication.Events.Event;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fran Firmino on 04/04/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    final Context context = this;

        //Add new animal to Firebase

        final public void addNewEvent(String eventDate, String time, String title, String description, String pic) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("/data/events/");

            String key = myRef.child("events").push().getKey();
            Event event= new Event(eventDate, time, title, description, pic);
            Map<String, Object> animalDetails = event.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, animalDetails);

            myRef.updateChildren(childUpdates);

            Toast.makeText(getApplicationContext(), title+" was added!", Toast.LENGTH_SHORT).show();

        }

    //-----Choose Photo--- CODE SOURCE:http://www.c-sharpcorner.com/UploadFile/e14021/capture-image-from-camera-and-selecting-image-from-gallery-o/---/
    public void selectImage(Context context) {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    // store image in Firebase
        public void saveImage(ImageView addImage, String uniqueID) {

            Uri downloadUrl = null;
            // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://taskyapplication-6a97d.appspot.com");

            // Create a reference to "mountains.jpg"
            StorageReference mountainsRef = storageRef.child("event_pics/events/" + uniqueID + ".jpg");

            // Get the data from an ImageView as bytes
            addImage.setDrawingCacheEnabled(true);
            addImage.buildDrawingCache();
            Bitmap bitmap = addImage.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data2 = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data2);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }






}
