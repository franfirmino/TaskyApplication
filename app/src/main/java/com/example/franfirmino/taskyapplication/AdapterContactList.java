package com.example.franfirmino.taskyapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import static com.example.franfirmino.taskyapplication.R.layout.list_contacts;

/**
 * Created by Fran Firmino on 05/03/2017.
 */

public class AdapterContactList extends ArrayAdapter<String> implements ListAdapter {

    private String   namesC;
    private String phones;
    private Context context;

    public AdapterContactList(Context context, String namesC, String phones){
        super(context, list_contacts);

        this.context = context;
        this.namesC= namesC;
        this.phones = phones;
    }

    /*@Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater lf  = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView = lf.inflate(R.layout.list_contacts, null);

        TextView nameTxt = (TextView) rowView.findViewById(R.id.contactName);
        TextView phoneTxt = (TextView) rowView.findViewById(R.id.phoneNo);

        //nameTxt.setText(namesC[position]);
        //phoneTxt.setText(phones[position]);
        return rowView;

    };*/

}
