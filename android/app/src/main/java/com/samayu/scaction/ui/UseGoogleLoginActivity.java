package com.samayu.scaction.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.samayu.scaction.R;
import com.samayu.scaction.service.SessionInfo;

public class UseGoogleLoginActivity extends AppCompatActivity {
    GoogleSignInClient googleSignInClient;
    SignInButton signInButton;
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_google_login);
        //signInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
       account = GoogleSignIn.getLastSignedInAccount(this);
       if(account!=null){
           SessionInfo.getInstance().setGoogleUserDetails(account);
           Intent intent=new Intent(UseGoogleLoginActivity.this,MainActivity.class);
           startActivity(intent);
       }
       Intent signInIntent = googleSignInClient.getSignInIntent();
       startActivityForResult(signInIntent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                 account = task.getResult(ApiException.class);
                 SessionInfo.getInstance().setGoogleUserDetails(account);
                Intent intent=new Intent(UseGoogleLoginActivity.this,MainActivity.class);
                startActivity(intent);

                // Signed in successfully, show authenticated UI.
                Log.i("SIGNIN", "signInResult:failed code=" + account.getPhotoUrl());
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.i("SIGNIN", "signInResult:failed code=" + e.getStatusCode());
               // updateUI(null);
            }
        }
    }
}
