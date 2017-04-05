package com.example.franfirmino.taskyapplication.ScheduleMessage;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import static android.R.id.message;
import static android.content.ContentValues.TAG;


/**
 * Created by Fran Firmino on 04/04/2017.
 */
public class AlarmService extends Service {

    private static final int SEND_SMS = 1;
    private String SPhone, SSms;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        // TODO Auto-generated method stub
        SPhone = i.getStringExtra("Number");
        SSms = i.getStringExtra("Message");

        sendMessage(SPhone,SSms);

        Log.d(TAG, "Alarm Started" + SPhone +SSms);


        return START_STICKY;
    }


    public void sendMessage(String SPhone, String SSms){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(SPhone, null, SSms, null, null);

    }


}

