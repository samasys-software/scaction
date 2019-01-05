package com.samayu.scaction.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.samayu.scaction.R;
import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.service.SessionInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class UseFacebookLoginActivity extends SCABaseActivity {
    //LoginButton loginButton;

    Button loginButton;
    TextView textView;
    CallbackManager callbackManager;
    public static final String FILE_NAME = "SCALogin.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_use_facebook_login);

        //loginButton = (LoginButton) findViewById(R.id.fb_login_id);
        loginButton = (Button) findViewById(R.id.fb_login_id);



        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

       boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){

            Intent intent=new Intent(UseFacebookLoginActivity.this,MainActivity.class);
            intent.putExtra("Registered",false);
            startActivity(intent);


        }
        else {
            LoginManager.getInstance().logInWithReadPermissions(UseFacebookLoginActivity.this, Arrays.asList("email"));
        }


//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        //loginButton.setReadPermissions("email");



        //loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                setFacebookData(loginResult);

            }

            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            //String email = response.getJSONObject().getString("email");

                            FBUserDetails fbUserDetails=new FBUserDetails();
                            fbUserDetails.setId(object.get("id").toString());
                            fbUserDetails.setName(object.get("name").toString());
                            fbUserDetails.setEmailAddress(object.get("email").toString());
                            JSONObject profile_pic_data= new JSONObject(object.get("picture").toString());
                           // fbUserDetails.setProfile_pic_data(profile_pic_data);
                           JSONObject pic_url=new JSONObject(profile_pic_data.getString("data"));
                           String url=pic_url.getString("url");
                           fbUserDetails.setUrl(url);
                            SessionInfo.getInstance().setFbUserDetails(fbUserDetails);
                            loginToFile(fbUserDetails,FILE_NAME);


//                            String firstName = response.getJSONObject().getString("first_name");
//                            String lastName = response.getJSONObject().getString("last_name");
//                            String middleName = response.getJSONObject().getString("middle_name");
                            String name = response.getJSONObject().getString("name");
                            String id = response.getJSONObject().getString("id");



                            Profile profile = Profile.getCurrentProfile();
                            if (Profile.getCurrentProfile() != null) {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                                id = profile.getId();
                                String link = profile.getLinkUri().toString();
                                Log.i("Link", link);

                            }

                            //Log.i("Login" + "Email", email);

                            Log.i("Login" + "name", name);
                            Log.i("Login" + "id", id);
                            //Log.i("Login" + "gender", gender);

                            // Log.i("Login" + "Gender", gender);

                            Intent intent=new Intent(UseFacebookLoginActivity.this,MainActivity.class);
                            intent.putExtra("Registered",false);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,picture.width(40).height(40)");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public void loginToFile(FBUserDetails details,String fileName) {
        File file = new File(getFilesDir(), fileName);
        file.delete();

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(fileName, UseFacebookLoginActivity.this.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(details);
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}