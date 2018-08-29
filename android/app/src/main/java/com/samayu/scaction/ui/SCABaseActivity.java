package com.samayu.scaction.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.samayu.scaction.R;
import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.service.SessionInfo;
import com.squareup.picasso.Picasso;

public abstract class SCABaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setContentView(int layoutId){
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_scabase, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutId, activityContainer, true);
        initializeDrawer(fullView);
        ImageView userImage = (ImageView) fullView.findViewById(R.id.userImage);
        TextView userName = (TextView)fullView.findViewById(R.id.userName);

        FBUserDetails fbUserDetails=SessionInfo.getInstance().getFbUserDetails();
        if(fbUserDetails!=null)
        {
            try{
                Picasso.with(this).load(fbUserDetails.getProfile_pic_url().getString("url"))
                        .into(userImage);
                userName.setText(fbUserDetails.getName());
            }
            catch (Exception e)
            {

            }
        }


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

    navigationView = (NavigationView) layout.findViewById(R.id.nav_view);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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




        } else if (id == R.id.castingCalls) {

        }

        return true;
    }
   // public abstract String getProdcastTitle();
    //public abstract boolean getCompanyName();



}