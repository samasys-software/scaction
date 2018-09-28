package com.samayu.scaction.ui;

/**
 * Created by NandhiniGovindasamy on 9/28/18.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.UserNotification;
import com.samayu.scaction.dto.UserRole;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 9/13/18.
 */

public class NotificationAdapter extends BaseAdapter {
    List<UserNotification> userNotifications;

    int count = 0;
    Context context;
    LayoutInflater inflater;

    ImageButton rmv, update, expiration;



    public NotificationAdapter(NotificationActivity mainActivity, List<UserNotification> userNotificationsList) {

        // TODO Auto-generated constructor stub
        userNotifications = userNotificationsList;


        context = mainActivity;
        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return userNotifications.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView message;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder;
        //if (convertView == null)
        {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.notifications_list, null);

            holder.message = (TextView) convertView.findViewById(R.id.message);

            convertView.setTag(holder);

            final UserNotification userNotification = userNotifications.get(position);


            holder.message.setText(userNotification.getMessage());



            return convertView;
        }

    }
}


