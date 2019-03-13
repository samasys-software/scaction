package com.samayu.scaction.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.UserNotification;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.adapter.NotificationAdapter;

import java.util.List;

public class NotificationActivity extends SCABaseActivity {
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        context=this;
        ListView listView=(ListView) findViewById(R.id.listOfNotifications);
        final List<UserNotification> userNotificationList= SessionInfo.getInstance().getUserNotifications();
        if(userNotificationList!=null){
            NotificationAdapter adapter=new NotificationAdapter(NotificationActivity.this,userNotificationList);
            listView.setAdapter(adapter);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserNotification userNotification=userNotificationList.get(position);
                Toast.makeText(context,userNotification.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
