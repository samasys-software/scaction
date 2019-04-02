package com.samayu.scaction.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.dto.ProfileDefaults;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.Listeners.RecyclerListener;
import com.samayu.scaction.ui.adapter.TalentListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SCABaseActivity {
    private boolean isNewUser;
    private RecyclerView talents;
    Context context;

    List<PortfolioPicture> portfolioPictures=null;
    List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        talents=(RecyclerView) findViewById(R.id.talents);
        GridLayoutManager layoutManager=new GridLayoutManager(MainActivity.this,2);
        talents.setHasFixedSize(true);
        talents.setLayoutManager(layoutManager);




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

        Call<List<User>> talentsDTOCall = new SCAClient().getClient().getActorProfiles(0,0);
        talentsDTOCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                 userList = response.body();
                talents.setAdapter(new TalentListAdapter(MainActivity.this,userList));

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }


        });


        talents.addOnItemTouchListener(new RecyclerListener(context,
                talents, new RecyclerListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                User user=userList.get(position);
                user.getUserId();
                Intent intent=new Intent(MainActivity.this,ViewPortfolioActivity.class);
                intent.putExtra("userId",user.getUserId());
                startActivity(intent);

                //here your logic
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));





    }

    private void registerNewUser(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertTheme);
        //alertDialog.setTitle();
        alertDialog.setCancelable(true);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View diaView = inflater.inflate(R.layout.alert_base, null);
        TextView sortTextHint=(TextView) diaView.findViewById(R.id.sortTextHint);
        sortTextHint.setText(getResources().getString(R.string.alert_for_register_as_user));

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
