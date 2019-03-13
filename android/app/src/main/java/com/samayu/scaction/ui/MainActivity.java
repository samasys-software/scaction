package com.samayu.scaction.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.ProfileDefaults;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SCABaseActivity {
    private boolean isNewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//                Boolean registered = getIntent().getExtras().getBoolean("Registered");
//                if(registered ) {
//                    User user=SessionInfo.getInstance().getUser();
//                    if(user!=null){
//                        addAdapter(user);
//                    }
//                } else {

                    if(SessionInfo.getInstance().getFbUserDetails()!=null) {
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
                                    getAllUserNotifications(user.getUserId());
                                    //addAdapter(user);
                                }

                            }


                            @Override
                            public void onFailure(Call<User> call, Throwable t) {


                            }
                        });
                    }


              //  }


            }

            @Override
            public void onFailure(Call<ProfileDefaults> call, Throwable t) {

            }


        });
    }

    private void registerNewUser(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertTheme);
        //alertDialog.setTitle();
        alertDialog.setCancelable(true);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View diaView = inflater.inflate(R.layout.alert_base, null);
        TextView sortTextHint=(TextView) diaView.findViewById(R.id.sortTextHint);
        sortTextHint.setText("Do You Want to Register in Start Camera, Action! ?");

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
                        Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                        intent.putExtra("isNew",true);
                        startActivity(intent);
                    }

                });
//alertDialog.show();
        final AlertDialog registerUser = alertDialog.create();
        registerUser.show();


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
}
