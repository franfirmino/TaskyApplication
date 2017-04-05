package com.example.franfirmino.taskyapplication.Events;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.franfirmino.taskyapplication.BaseActivity;
import com.example.franfirmino.taskyapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * Created by Fran Firmino on 05/04/2017.
 */

public class CustomAdapter extends ArrayAdapter<Event> implements View.OnClickListener {

        private ArrayList<Event> animal;
        Context mContext;
        String opt;
        Uri uri;

        // View lookup cache
        private static class ViewHolder {
            TextView txtName;
            TextView txtDate;
            ImageView info;


        }

        public CustomAdapter(ArrayList<Event> data, Context context) {
            super(context, R.layout.list_view_events, data);
            this.animal = data;
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();
            Object object = getItem(position);
            Event event = (Event) object;

            switch (v.getId()) {
                case R.id.title:
                    Snackbar.make(v, "Release date " + event.getTitle(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                    break;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Event event = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            final ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.list_view_events, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.title);
                viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date);
                viewHolder.info = (ImageView) convertView.findViewById(R.id.icon);

                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtName.setText(event.getTitle());
            viewHolder.txtDate.setText(event.getEventDate());
            //viewHolder.info.setOnClickListener(this);

            String picName = event.getPic();

            // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();


            storageRef.child(picName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri u) {
                    Log.e("Got the URL", "" + u);
                    Glide.with(mContext)
                            .load(u)
                            .into(viewHolder.info);
                    // Got the download URL for 'users/me/profile.png'

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("ERROR", "" + exception);
                    // Handle any errors
                }
            });

            //viewHolder.info.setImageResource(uri);
            return convertView;
        }


}

