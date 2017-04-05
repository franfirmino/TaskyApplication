package com.example.franfirmino.taskyapplication.ScheduleMessage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.franfirmino.taskyapplication.R;

import java.util.ArrayList;

public class ContactsDisplayActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<Contact> Contacts, contactList;
    private static CustomAdapter adapter;
    Cursor cursor ;
    String contactName, contactNo ;
    Context context = this;
    public  static final int RequestPermissionCode  = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);

        listView = (ListView) findViewById(R.id.listView);
        Contacts = new ArrayList<Contact>();

        EnableRuntimePermission();
        GetContactsIntoArrayList();
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();



        adapter = new CustomAdapter(this,contactList);

        listView.setAdapter(adapter);

        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contactName = contactList.get(+position).getName();
                String contactNo = contactList.get(+position).getPhone();

                Intent intent = new Intent(getApplicationContext(), ScheduleMessage.class);
                intent.putExtra("Name",contactName);
                intent.putExtra("Phone",contactNo);
                startActivity(intent);
            }
        });


    }


    public void GetContactsIntoArrayList(){

        contactList = new ArrayList<Contact>();
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            contactName = (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contactNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Contact contact = new Contact();
            contact.setName(contactName);
            contact.setPhone(contactNo);

            contactList.add(contact);

        }

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ContactsDisplayActivity.this,
                Manifest.permission.READ_CONTACTS))
        {
            System.out.println("CONTACTS permission allows us to Access CONTACTS app");
        } else {
            ActivityCompat.requestPermissions(ContactsDisplayActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission Granted, Now your application can access CONTACTS.");

                } else {
                    System.out.println("Permission Canceled, Now your application cannot access CONTACTS.");
                }
                break;
        }
    }
}
