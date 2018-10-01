package com.samayu.scaction.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserNotification;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyCastingCallActivity extends AppCompatActivity {

    Button registerToApply,apply;
    ImageButton edit;
    Context context;
    User user;
    TextView projectName,projectDetails,productionCompany,role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_casting_call);
        context=this;
        registerToApply=(Button) findViewById(R.id.registerToApply);
        apply=(Button) findViewById(R.id.apply);
        edit=(ImageButton) findViewById(R.id.edit);

        projectName=(TextView) findViewById(R.id.displayProjectName);
        projectDetails=(TextView) findViewById(R.id.displayProjectDetails);
        productionCompany=(TextView) findViewById(R.id.displayProductionCompany);
        role=(TextView) findViewById(R.id.displayRole);

        final CastingCall currentCastingCall= SessionInfo.getInstance().getCurrentCastingCall();
        if(currentCastingCall!=null){
            projectName.setText(currentCastingCall.getProjectName());
            projectDetails.setText(currentCastingCall.getProjectDetails());
            productionCompany.setText(currentCastingCall.getProductionCompany());
            role.setText(currentCastingCall.getRoleDetails());
        }

        user=SessionInfo.getInstance().getUser();
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
                intent.putExtra("isNew",true);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ApplyCastingCallActivity.this,CreateUserCastingCallsActivity.class);
                intent.putExtra("isNew",false);
                startActivity(intent);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> applyCastingCallDTOCall = new SCAClient().getClient().applyCastingCall(currentCastingCall.getId(),user.getUserId());
                applyCastingCallDTOCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("Success","kkHai");


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });
    }
}
