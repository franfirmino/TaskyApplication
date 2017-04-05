package com.example.franfirmino.taskyapplication.ScheduleMessage;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

import com.example.franfirmino.taskyapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ScheduleMessage extends AppCompatActivity {


    private String name, phone, message;
    private Integer myHour, myMin, myDay, myMonth, myYear;
    private DatePicker dateT;
    private TimePicker timeT;
    private Button sendMsg;
    private PendingIntent pendingIntent;
    private EditText messageTxt;
    private TextView dateShow, timeShow, nameMsg;
    private Button datePick, timePick;
    private ArrayList<Integer> date;

    public static final int SEND_SMS = 101;
    public  static final int RequestPermissionCode  = 2 ;
    static final int DATE_PICKER_ID = 1111;
    static final int TIMER_PICKER_ID = 1112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_message);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        phone = intent.getStringExtra("Phone");

        dateT = (DatePicker)findViewById(R.id.dateTxt);
        timeT = (TimePicker) findViewById(R.id.timeTxt);
        sendMsg = (Button) findViewById(R.id.sendBtn);
        messageTxt = (EditText) findViewById(R.id.messageTxt);
        timeShow = (TextView) findViewById(R.id.timeShow);
        dateShow = (TextView) findViewById(R.id.dateShow);
        nameMsg  = (TextView) findViewById(R.id.nameMsg);

        timePick = (Button) findViewById(R.id.timePick);
        datePick = (Button) findViewById(R.id.datePick);

        nameMsg.setText(getString(R.string.send_msg_to)+name);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = String.valueOf(messageTxt.getText());

                ScheduleTextMessage(phone, message, myMonth, myYear, myDay, myHour, myMin);

            }
        });

        // Get current date by calendar
        final Calendar c = Calendar.getInstance();
        myYear  = c.get(Calendar.YEAR);
        myMonth = c.get(Calendar.MONTH);
        myDay   = c.get(Calendar.DAY_OF_MONTH);
        myHour = c.get(Calendar.HOUR_OF_DAY);
        myMin = c.get(Calendar.MINUTE);

        // set current time into timepicker
        timeT.setCurrentHour(myHour);
        timeT.setCurrentMinute(myMin);

        EnableRuntimePermission();

    }

    @Override
    protected void onStart(){
        super.onStart();
        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_PICKER_ID);
            }
        });

        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(TIMER_PICKER_ID);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void ScheduleTextMessage(String Phone, String Message, int month, int year, int day, int hour, int minute ) {


        if(Message ==null || Message.length() < 2) {
            messageTxt.setError("Message Missing");

        }else{

            Intent myIntent = new Intent(ScheduleMessage.this, AlarmReceiver.class);
            Bundle bundle = new Bundle();
            bundle.putString("Number", Phone);
            bundle.putString("Message", Message);
            myIntent.putExtras(bundle);

            pendingIntent = PendingIntent.getBroadcast(ScheduleMessage.this, 0, myIntent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //calendar.add(Calendar.SECOND, 10);
            calendar.set(year, month, day, hour, minute);

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            messageTxt.setText("");
            nameMsg.setText(R.string.msg_scheduled);
            timeShow.setText("");
            dateShow.setText("");

            Toast.makeText(ScheduleMessage.this, R.string.msg_scheduled, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                return new DatePickerDialog(this, pickerListener, myYear, myMonth, myDay);

            case TIMER_PICKER_ID:
                return new  TimePickerDialog(this, mTimeSetListener, myHour, myMin, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myHour = hourOfDay;
                    myMin = minute;

                    timeShow.setText(myHour+":"+myMin);

                }
            };

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            myYear  = selectedYear;
            myMonth = selectedMonth;
            myDay  = selectedDay;

            dateShow.setText(myDay +"/"+myMonth+"/"+myYear);

            // Show selected date
            date = new ArrayList<Integer>();

            date.add(myYear);
            date.add(myMonth);
            date.add(myDay);

        }
    };


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ScheduleMessage.this,
                Manifest.permission.SEND_SMS))
        {
            System.out.println(getString(R.string.permission_SMS));
        } else {
            ActivityCompat.requestPermissions(ScheduleMessage.this,new String[]{
                    Manifest.permission.SEND_SMS}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission Granted, Now your application can SEND MESSAGES");

                } else {
                    System.out.println("Permission Canceled, Now your application cannot SEND MESSAGES.");
                }
                break;
        }
    }
}



