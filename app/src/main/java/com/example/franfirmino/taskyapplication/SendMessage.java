package com.example.franfirmino.taskyapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SendMessage extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor ;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        listView = (ListView) findViewById(R.id.listView);
        StoreContacts = new ArrayList<String>();

        EnableRuntimePermission();
        GetContactsIntoArrayList();

        arrayAdapter = new ArrayAdapter<String>(
                SendMessage.this,
                R.layout.list_contacts,
                R.id.contactName, StoreContacts

        );
        listView.setAdapter(arrayAdapter);

        // Locate the EditText in listview_
        final EditText searchTxt = (EditText) findViewById(R.id.searchText);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedContact = StoreContacts.get(+position);

                /*Intent intent = new Intent(getApplicationContext(), AnimalProfile.class);
                intent.putExtra("Name",clickedContact);
                intent.putExtra("Option",opt);
                startActivity(intent);*/

                Toast.makeText(getApplicationContext(), "Picked: " +clickedContact, Toast.LENGTH_SHORT).show();
            }
        });

        // Capture Text in EditText
        searchTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = searchTxt.getText().toString().toLowerCase(Locale.getDefault());
                arrayAdapter.getFilter().filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + "\n" + phonenumber);
        }
        cursor.close();
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                SendMessage.this,
                Manifest.permission.READ_CONTACTS))
        {
            System.out.println("CONTACTS permission allows us to Access CONTACTS app");
        } else {
            ActivityCompat.requestPermissions(SendMessage.this,new String[]{
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
