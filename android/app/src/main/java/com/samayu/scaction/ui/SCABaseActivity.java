package com.samayu.scaction.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
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
import io.github.pixee.security.ObjectInputFilters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

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
        final ImageView logo = (ImageView) fullView.findViewById(R.id.logo);
        notificationAlert= (ImageView) fullView.findViewById(R.id.notificationAlert);
        Button loginFB=(Button) fullView.findViewById(R.id.fb_login_id);
        TextView userName = (TextView)fullView.findViewById(R.id.userName);
        boolean isLoggedIn=false;
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null){

            isLoggedIn=  accessToken != null && !accessToken.isExpired() ;

        }
        GoogleSignInAccount accessToken1=  GoogleSignIn.getLastSignedInAccount(this);
        if(accessToken1!=null){
            isLoggedIn=  accessToken1 != null  ;
        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SCABaseActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

     //   boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if(isLoggedIn){
//            String url=null;
//            String displayName=null;
//          if(loginType==0) {
              FBUserDetails fbUserDetails = loginRetrive(FILE_NAME);
              if(fbUserDetails!=null) {
                  SessionInfo.getInstance().setFbUserDetails(fbUserDetails);
                  //url="http://graph.facebook.com/" + fbUserDetails.getId() + "/picture?width=200&height=200";
                  String url = fbUserDetails.getUrl();
                  String displayName = fbUserDetails.getName();
                  loginFB.setVisibility(View.GONE);
                  try{
                      Picasso.with(this).load(url)
                              .into(userImage);
                      userName.setText(displayName);
                  }
                  catch (Exception e)
                  {

                  }
              }
        }
        else{
            userName.setVisibility(View.GONE);
            userImage.setVisibility(View.GONE);
            notificationAlert.setVisibility(View.INVISIBLE);
        }
        loginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SCABaseActivity.this, R.style.AlertTheme);
                //alertDialog.setTitle();
                alertDialog.setCancelable(true);
                LayoutInflater inflater = SCABaseActivity.this.getLayoutInflater();
                View diaView = inflater.inflate(R.layout.login_base, null);
                SignInButton googleSignIn=(SignInButton) diaView.findViewById(R.id.googleSignIn);
                Button fbSignIn=(Button) diaView.findViewById(R.id.fbSignIn);


                alertDialog.setView(diaView);

                googleSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent(SCABaseActivity.this,UseGoogleLoginActivity.class);
                        startActivity(intent);

                    }
                });

                fbSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent intent=new Intent(SCABaseActivity.this,UseFacebookLoginActivity.class);
                         startActivity(intent);
                    }
                });

//                alertDialog.setNegativeButton("CANCEL",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Write your code here to execute after dialog
//                                dialog.cancel();
//
//                            }
//                        });
//
//                alertDialog.setPositiveButton("Ok",
//                        new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
//                                intent.putExtra("isNew",true);
//                                startActivity(intent);
//                            }
//
//                        });
////alertDialog.show();
                final AlertDialog registerUser = alertDialog.create();
                registerUser.show();



            }
        });
        //setOrderTotal();

        List<UserNotification> userNotifications=SessionInfo.getInstance().getUserNotifications();
        if(userNotifications!=null){
            setNotificationCount(userNotifications);
        }
        //image.setImageDrawable(buildCounterDrawable(2));

//        FBUserDetails fbUserDetails=SessionInfo.getInstance().getFbUserDetails();
//        if(fbUserDetails!=null)
//        {
//            try{
//                Picasso.with(this).load(fbUserDetails.getUrl())
//                        .into(userImage);
//                userName.setText(fbUserDetails.getName());
//            }
//            catch (Exception e)
//            {
//
//            }
//        }
//


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
                                intent=new Intent(SCABaseActivity.this,UserCastingCallsActivity.class);
                                intent.putExtra("UserCastingCall",2);
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
                                File dir =getFilesDir();
                                File file = new File(dir, FILE_NAME);

                                boolean deleted = file.delete();
                               if(SessionInfo.getInstance().getFbUserDetails().getLoginType()==0){
                                   LoginManager.getInstance().logOut();

                               }
                               else {
                                   GoogleSignInClient googleSignInClient;
                                   GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                           .requestEmail()
                                           .build();
                                   googleSignInClient = GoogleSignIn.getClient(SCABaseActivity.this, gso);
                                   googleSignInClient.signOut();

                                   googleSignInClient.revokeAccess();

                               }

                                SessionInfo.getInstance().destroy();



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
        //getSupportActionBar().setLogo(R.drawable.logo);
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
            intent.putExtra("UserCastingCall",1);
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
            ObjectInputFilters.enableObjectFilterIfUnprotected(ois);
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

    public ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("One Moment Please");
        //p.show();
        return progressDialog;
    }
    public void loginToFile(FBUserDetails details, String fileName, Activity activity) {
        File file = new File(getFilesDir(), fileName);
        file.delete();

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(fileName, activity.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(details);
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


