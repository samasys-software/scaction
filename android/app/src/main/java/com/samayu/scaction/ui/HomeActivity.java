package com.samayu.scaction.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.samayu.scaction.R;
import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.ProfileDefaults;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserNotification;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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











