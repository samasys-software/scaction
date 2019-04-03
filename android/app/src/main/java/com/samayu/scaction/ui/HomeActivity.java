package com.samayu.scaction.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.adapter.UserRolesAdapter;

import java.util.List;

public class HomeActivity extends SCABaseActivity {

    boolean useFacebookLogin=false;


    ListView listView;
    TextView userName;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=this;
        listView=(ListView) findViewById(R.id.listOfRoles);
        userName=(TextView) findViewById(R.id.screenName);
        User user=SessionInfo.getInstance().getUser();
        addAdapter(user);




      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserRole userRole= SessionInfo.getInstance().getUser().getUserRoles().get(position);


            }
        });*/

    }





    private void addAdapter(User currentUser){
        System.out.println(currentUser.toString());
        userName.setText(currentUser.getScreenName());
        getAllUserNotifications(currentUser.getUserId());

        List<UserRole> userRoles = currentUser.getUserRoles();

        UserRolesAdapter adapter = new UserRolesAdapter(HomeActivity.this, userRoles);

       listView.setAdapter(adapter);
    }


}











