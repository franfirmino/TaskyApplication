package com.example.franfirmino.taskyapplication.ScheduleMessage;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.franfirmino.taskyapplication.R;

import java.util.ArrayList;

/**
 * Created by Fran Firmino on 05/03/2017.
 */

public class CustomAdapter extends ArrayAdapter<Contact> implements Filterable {

     private Context mContext;
     private ArrayList<Contact> contact; //values to be displayed
     private ArrayList<Contact> originContact; // original values
     LayoutInflater inflater;
     private RelativeLayout listContacts;

    // View lookup cache
    private static class ViewHolder {
        TextView contactName;
        TextView contactPhone;

    }

    public CustomAdapter(Context context, ArrayList<Contact> myContacts) {
        super(context, R.layout.list_contacts, myContacts);
        this.originContact= myContacts;
        this.contact = myContacts;
        this.mContext=context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return contact.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Contact contact =(Contact)object;
    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_contacts, parent, false);
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.contactPhone = (TextView) convertView.findViewById(R.id.contactNo);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.contactName.setText(contact.get(position).getName());
        viewHolder.contactPhone.setText(contact.get(position).getPhone());
        //viewHolder.info.setOnClickListener(this);

        Log.e("Contact name", ""+ contact.get(position).getName());
        Log.e("Contact Phone", ""+contact.get(position).getPhone());


        //viewHolder.info.setImageResource(uri);
        return convertView;
    };

}


