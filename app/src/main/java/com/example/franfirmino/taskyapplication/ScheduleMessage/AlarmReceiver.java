package com.example.franfirmino.taskyapplication.ScheduleMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by Fran Firmino on 04/04/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{

    public static final String ACTION_REFRESH_ALARM ="com.paad.network.ACTION_REFRESH_ALARM";

        @Override
        public void onReceive(Context myContext, Intent myDetails) {
            try {
                Intent myService = new Intent(myContext, AlarmService.class);
                Bundle bundle = myDetails.getExtras();
                myService.putExtras(bundle);
                myContext.startService(myService);

                Log.d(TAG, "Alarm Receiver Running");

            } catch (Exception e) {
                Toast.makeText(myContext, "There was an error somewhere, but we still received an alarm" + e, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

}
