package com.samayu.scaction.ui;

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
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.samayu.scaction.R;
import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends SCABaseActivity {

    boolean useFacebookLogin=false;
    boolean isNewUser;
    String user_id,user_name,user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String user_id=SessionInfo.getInstance().getFbUserDetails().getId();




        Call<User> checkUserDTOCall= new SCAClient().getClient().checkUser(user_id);
        checkUserDTOCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                    User loginDTO = response.body();
                    if(loginDTO!=null) {
                        isNewUser = false;
                    }
                    else{
                        isNewUser=false;
                        registerNewUser();
                    }





            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


            }
        });




    }

    private void registerNewUser(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this, R.style.AlertTheme);
        alertDialog.setTitle(" Sorting Details");
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
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,((ActionMenuView)findViewById(R.id.actionMenuView)).getMenu());
//        return true;
//    }




}











