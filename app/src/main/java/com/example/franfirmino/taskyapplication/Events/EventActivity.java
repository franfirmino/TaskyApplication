package com.example.franfirmino.taskyapplication.Events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.franfirmino.taskyapplication.BaseActivity;
import com.example.franfirmino.taskyapplication.R;

import java.util.Calendar;
import java.util.UUID;

public class EventActivity extends BaseActivity {


    private FloatingActionButton addImageBtn;
    private ImageView addImage;
    private EditText desTxt, titleTxt;
    private TextView  dateTxt, timeTxt;
    private Button createBtn;
    private int myDay, myMonth, myYear;
    private int myHour, myMin;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    final Context context = this;

    static final int DATE_PICKER_ID = 1112;
    static final int TIMER_PICKER_ID = 1113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        addImage = (ImageView) findViewById(R.id.addImage);
        createBtn  = (Button) findViewById(R.id.createBtn);

        desTxt = (EditText) findViewById(R.id.descriptionTxt);
        titleTxt = (EditText) findViewById(R.id.titleTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);
        timeTxt = (TextView) findViewById(R.id.timeTxt);

        // Get current date by calendar
        final Calendar c = Calendar.getInstance();
        myYear  = c.get(Calendar.YEAR);
        myMonth = c.get(Calendar.MONTH);
        myDay   = c.get(Calendar.DAY_OF_MONTH);
        myHour = c.get(Calendar.HOUR_OF_DAY);
        myMin = c.get(Calendar.MINUTE);


        addImageBtn = (FloatingActionButton) findViewById(R.id.addImageBtn);

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
            });

        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIMER_PICKER_ID);
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(context);
            }
         });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                    sendFormDetails();

            }
            });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
               if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Drawable d = new BitmapDrawable(getResources(), imageBitmap);
                    addImage.setImageDrawable(d);
                }

            } else if (requestCode == 2) {
                Uri imageUri = data.getData();
                addImage.setImageURI(null);
                addImage.setImageURI(imageUri);



            }
        }
    }

    //-----Choose Photo--- CODE SOURCE:http://www.c-sharpcorner.com/UploadFile/e14021/capture-image-from-camera-and-selecting-image-from-gallery-o/---/
    public void selectImage(Context context) {

        final CharSequence[] options = { getString(R.string.take_photo), getString(R.string.choose_gallery),(getString(R.string.cancel))};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.add_photo);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.take_photo)))
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                else if (options[item].equals(getString(R.string.choose_gallery)))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //--------------Sending Data to Database -------------------------------------------------/
    public void sendFormDetails(){

            String title = String.valueOf(titleTxt.getText());
            String date = String.valueOf(dateTxt.getText());
            String time = String.valueOf(timeTxt.getText());
            String descr = String.valueOf(desTxt.getText());
            String uniqueID = UUID.randomUUID().toString();

                String pic = "event_pics/events/" + uniqueID + ".jpg";

                saveImage(addImage, uniqueID);

                addNewEvent(date, time, title, descr, pic);



    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            myYear  = selectedYear;
            myMonth = selectedMonth+1;
            myDay  = selectedDay;

            dateTxt.setText(myDay+"/"+myMonth+"/"+myYear);

        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myHour = hourOfDay;
                    myMin = minute;
                    timeTxt.setText(myHour+":"+myMin);

                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                return new DatePickerDialog(this, pickerListener, myYear, myMonth, myDay);

            case TIMER_PICKER_ID:
                return new TimePickerDialog(this, mTimeSetListener, myHour, myMin, false);
        }
        return null;
    }



}
