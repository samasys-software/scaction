package com.samayu.scaction.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SessionInfo;

public class ApplyCastingCallActivity extends AppCompatActivity {

    Button registerToApply,apply;
    ImageButton edit;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_casting_call);
        context=this;
        registerToApply=(Button) findViewById(R.id.registerToApply);
        apply=(Button) findViewById(R.id.apply);
        edit=(ImageButton) findViewById(R.id.edit);

        CastingCall currentCastingCall= SessionInfo.getInstance().getCurrentCastingCall();

        User user=SessionInfo.getInstance().getUser();
        if(user==null){
            registerToApply.setVisibility(View.VISIBLE);
        }
        else{
            if(currentCastingCall.getUserId()==user.getUserId()){
                edit.setVisibility(View.VISIBLE);
            }
            else{
                apply.setVisibility(View.VISIBLE);
            }
        }
        registerToApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ApplyCastingCallActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ApplyCastingCallActivity.this,CreateUserCastingCallsActivity.class);
                startActivity(intent);
            }
        });
    }
}
