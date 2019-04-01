package com.samayu.scaction.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.samayu.scaction.R;
import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.UserNotification;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SCABaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView popupMenuView;
    ImageView notificationAlert;
    public static final String FILE_NAME = "SCALogin.txt";
    List<City> cityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());


    }

    @Override
    public void setContentView(int layoutId){
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_scabase, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutId, activityContainer, true);
        initializeDrawer(fullView);
        NavigationView navigationView = (NavigationView) fullView.findViewById(R.id.nav_view);
        popupMenuView = (NavigationView) fullView.findViewById(R.id.popup_menu_view);

        final ImageView userImage = (ImageView) fullView.findViewById(R.id.userImage);
        notificationAlert= (ImageView) fullView.findViewById(R.id.notificationAlert);
        Button loginFB=(Button) fullView.findViewById(R.id.fb_login_id);
        TextView userName = (TextView)fullView.findViewById(R.id.userName);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
            FBUserDetails fbUserDetails=loginRetrive(FILE_NAME);
            SessionInfo.getInstance().setFbUserDetails(fbUserDetails);
            loginFB.setVisibility(View.GONE);



        }
        else{
            userName.setVisibility(View.GONE);
            userImage.setVisibility(View.GONE);
            notificationAlert.setVisibility(View.INVISIBLE);
        }
        loginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SCABaseActivity.this,UseFacebookLoginActivity.class);
                startActivity(intent);
            }
        });
        //setOrderTotal();

        List<UserNotification> userNotifications=SessionInfo.getInstance().getUserNotifications();
        if(userNotifications!=null){
            setNotificationCount(userNotifications);
        }
        //image.setImageDrawable(buildCounterDrawable(2));

        FBUserDetails fbUserDetails=SessionInfo.getInstance().getFbUserDetails();
        if(fbUserDetails!=null)
        {
            try{
                Picasso.with(this).load(fbUserDetails.getUrl())
                        .into(userImage);
                userName.setText(fbUserDetails.getName());
            }
            catch (Exception e)
            {

            }
        }

        notificationAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SCABaseActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), userImage);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;

                        switch (item.getItemId())
                        {
                            case R.id.myHome:
                                intent=new Intent(SCABaseActivity.this,HomeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.myJobs:
                                intent=new Intent(SCABaseActivity.this,HomeActivity.class);

                                startActivity(intent);
                                break;
                            case R.id.myPortfolio:
                                intent=new Intent(SCABaseActivity.this,CreatePortfolioActivity.class);
                                startActivity(intent);
                                break;

                            case R.id.myProfile:
                                intent=new Intent(SCABaseActivity.this,ProfileActivity.class);
                                intent.putExtra("isNew",false);
                                startActivity(intent);
                                break;

                            case R.id.signout:
                                LoginManager.getInstance().logOut();
                                SessionInfo.getInstance().destroy();

                                File dir =getFilesDir();
                                File file = new File(dir, FILE_NAME);

                                boolean deleted = file.delete();

                                intent=new Intent(SCABaseActivity.this,MainActivity.class);
                                startActivity(intent);
                                break;

                        }
                        return false;
                    }
                });


                popupMenu.inflate(R.menu.profile_menu);
                popupMenu.show();

            }
        });


      /*  boolean companyName=getCompanyName();
        if(companyName){
            distributorName.setText("Hello");

        }*/
//        else
//        {
//            NavigationView navigation = (NavigationView) fullView.findViewById(R.id.nav_view);
//            Menu nav_Menu = navigation.getMenu();
//            nav_Menu.findItem(R.id.nav_bar).setVisible(false);
//            nav_Menu.findItem(R.id.nav_changePassword).setVisible(true);
//            nav_Menu.findItem(R.id.nav_changeStore).setVisible(true);
//            nav_Menu.findItem(R.id.nav_editRegistration).setVisible(true);
//            nav_Menu.findItem(R.id.nav_orderEntry).setVisible(false);
//            nav_Menu.findItem(R.id.nav_orderHistroy).setVisible(false);
//        }

       //screenName.setText("hai");//getProdcastTitle().toUpperCase());



        super.setContentView(fullView);

    }

    protected void initializeDrawer(DrawerLayout layout){
        Toolbar toolbar = (Toolbar)layout.findViewById(R.id.proToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.logo);
        DrawerLayout drawer = layout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

   NavigationView navigationView = (NavigationView) layout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_scabase, null);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu navigation_menu) {
        // Inflate the navigation_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.navigation_menu.navigation_drawer, navigation_menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        final Intent intent;
        final Bundle b;
        if (id == R.id.talent) {

            PopupMenu popupMenu = new PopupMenu(getApplicationContext(),popupMenuView);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId())
                    {

                    }
                    return false;
                }
            });


            popupMenu.inflate(R.menu.talent_sub_menu);
            popupMenu.show();

        }
        else if (id == R.id.castingCalls) {
            intent=new Intent(SCABaseActivity.this,UserCastingCallsActivity.class);
            intent.putExtra("UserCastingCall",false);
            startActivity(intent);

        }
        else if(id==R.id.locations){

        }
        else if(id==R.id.studios){

        }
        else if(id==R.id.movieConsulting){

            PopupMenu popupMenu = new PopupMenu(getApplicationContext(),popupMenuView);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId())
                    {

                    }
                    return false;
                }
            });


            popupMenu.inflate(R.menu.movie_consulting_sub_menu);
            popupMenu.show();

        }

        return true;
    }

   public void setNotificationCount(List<UserNotification> userNotificationList)  {
       //int count=0;
       if(userNotificationList.size()!=0) {
           notificationAlert.setImageDrawable(buildCounterDrawable(userNotificationList.size()));
       }

   }




    private Drawable buildCounterDrawable(int count) {
        /*if(count ==0)
            return new BitmapDrawable(getResources(), createImage(1,1)) ;
         */

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.notification, null);
        //view.setBackgroundResource(backgroundImageId);
        Bitmap bitmap = null;


        /*if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.count);
            counterTextPanel.setVisibility(View.GONE);
           view.setVisibility(View.GONE);
        } else {*/

        TextView textView = (TextView) view.findViewById(R.id.count);
        textView.setText(" " + count+" ");
        //}


        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int measureWidth = view.getMeasuredWidth();
        int measureHeight = view.getMeasuredHeight();
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());


        view.setDrawingCacheEnabled(true);
        bitmap= Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheEnabled(false);



        return new BitmapDrawable(getResources(), bitmap);

        //return view;
    }



    public  FBUserDetails loginRetrive(String fileName) {
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput(fileName));
            FBUserDetails r = (FBUserDetails) ois.readObject();
            return r;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getAllUserNotifications(long userId){

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

    public void setRolesInRecyclerView(List<ProfileType> profileTypes)
    {
        List<SelectedCastingCallRoles> selectedCastingCallRolesList=new ArrayList<SelectedCastingCallRoles>();

        for(ProfileType profileType:profileTypes) {

            SelectedCastingCallRoles selectedCastingCallRoles = new SelectedCastingCallRoles();
            selectedCastingCallRoles.setChecked(false);
            selectedCastingCallRoles.setProfileType(profileType);
            selectedCastingCallRolesList.add(selectedCastingCallRoles);

        }

        SessionInfo.getInstance().setRolesList(selectedCastingCallRolesList);


    }
}


