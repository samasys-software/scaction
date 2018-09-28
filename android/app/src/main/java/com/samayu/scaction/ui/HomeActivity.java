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
    boolean isNewUser;


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

        Call<ProfileDefaults> profileDefaultsCall = new SCAClient().getClient().getProfileDefaults();
        profileDefaultsCall.enqueue(new Callback<ProfileDefaults>() {
            @Override
            public void onResponse(Call<ProfileDefaults> call, Response<ProfileDefaults> response) {

                ProfileDefaults profileDefaults = response.body();

                List<Country> countries=profileDefaults.getCountries();

                Country defaultCountry = new Country();
                defaultCountry.setId(0);
                defaultCountry.setIsdCode("");
                defaultCountry.setCode("");
                defaultCountry.setName("Select Country");
                countries.add(0, defaultCountry  );

                SessionInfo.getInstance().setCountries(countries);

                List<ProfileType> profileTypes=profileDefaults.getProfileTypes();
                SessionInfo.getInstance().setProfileTypes(profileTypes);

            }

            @Override
            public void onFailure(Call<ProfileDefaults> call, Throwable t) {

            }


        });

        Boolean registered = getIntent().getExtras().getBoolean("Registered");
        if(registered ) {
            User user=SessionInfo.getInstance().getUser();
            if(user!=null){
                addAdapter(user);
            }
        } else {


            Call<User> checkUserDTOCall = new SCAClient().getClient().checkUser(SessionInfo.getInstance().getFbUserDetails().getId());
            checkUserDTOCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                        User user = response.body();

                        if (user == null) {
                            isNewUser = false;
                            registerNewUser();
                        } else {
                            SessionInfo.getInstance().setUser(user);
                            addAdapter(user);
                        }

                    }




                @Override
                public void onFailure(Call<User> call, Throwable t) {


                }
            });


        }

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserRole userRole= SessionInfo.getInstance().getUser().getUserRoles().get(position);


            }
        });*/

    }

    private void registerNewUser(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this, R.style.AlertTheme);
        //alertDialog.setTitle();
        alertDialog.setCancelable(true);
        LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
        View diaView = inflater.inflate(R.layout.alert_base, null);
        alertDialog.setView(diaView);

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();

                    }
                });

        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
                        startActivity(intent);
                    }

                });
//alertDialog.show();
        final AlertDialog checkout = alertDialog.create();
        checkout.show();


      /*  CreateUser createUser=new CreateUser();
        createUser.setFbUser("Nandhini Govindasamy Thevaraya Pillai");
        createUser.setFbEmail("nandini-14@outlook.com");
        createUser.setProfile_pic("https://graph.facebook.com/2183841428553289?fields=picture.width(720).height(720)");




        Call<User> registerNewUserCall = new SCAClient().getClient().registerNewUser(createUser);
            registerNewUserCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.i("Success","Hai");

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });*/
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu navigation_menu) {
//        getMenuInflater().inflate(R.navigation_menu.navigation_menu,((ActionMenuView)findViewById(R.id.actionMenuView)).getMenu());
//        return true;
//    }

    private void addAdapter(User currentUser){
        System.out.println(currentUser.toString());
        userName.setText(currentUser.getScreenName());
        getAllUserNotifications(currentUser.getUserId());

        List<UserRole> userRoles = currentUser.getUserRoles();

        UserRolesAdapter adapter = new UserRolesAdapter(HomeActivity.this, userRoles);

       listView.setAdapter(adapter);
    }
    private void getAllUserNotifications(long userId){

        Call<List<UserNotification>> userNotificationDTOCall = new SCAClient().getClient().getUserNotifications(userId);
        userNotificationDTOCall.enqueue(new Callback<List<UserNotification>>() {
            @Override
            public void onResponse(Call<List<UserNotification>> call, Response<List<UserNotification>> response) {
                Log.i("Success","Hai");
                List<UserNotification> userNotifications=response.body();
                SessionInfo.getInstance().setUserNotifications(userNotifications);
                setNotificationCount(userNotifications);
                System.out.println(userNotifications.toString());

            }

            @Override
            public void onFailure(Call<List<UserNotification>> call, Throwable t) {

            }
        });

    }

}











