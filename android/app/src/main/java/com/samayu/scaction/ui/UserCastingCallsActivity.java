package com.samayu.scaction.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCastingCallsActivity extends SCABaseActivity {

    ListView castingCalls;


    ImageButton addNew;
    LinearLayout castingCallHeader;
    Context context;
    CastingCallsAdapter adapter;
    List<CastingCall> castingCallsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_casting_calls);
        context=this;
        castingCalls=(ListView) findViewById(R.id.listOfCastingCalls);
        addNew=(ImageButton) findViewById(R.id.addNewCastingCall);
        castingCallHeader=(LinearLayout) findViewById(R.id.castingCallHeader);

        Boolean userCastingCall = getIntent().getExtras().getBoolean("UserCastingCall");
        if(userCastingCall ) {
            Call<List<CastingCall>> getUserCastingCallsDTOCall = new SCAClient().getClient().getMyCastingCalls(SessionInfo.getInstance().getUser().getUserId());
            getUserCastingCallsDTOCall.enqueue(new Callback<List<CastingCall>>() {
                @Override
                public void onResponse(Call<List<CastingCall>> call, Response<List<CastingCall>> response) {
                    castingCallsList=response.body();
                    setAdapterToView(castingCallsList);


                }




                @Override
                public void onFailure(Call<List<CastingCall>> call, Throwable t) {


                }
            });

        }

        else{
            addNew.setVisibility(View.GONE);
            Call<List<CastingCall>> getUserCastingCallsDTOCall = new SCAClient().getClient().getAllCastingCalls();
            getUserCastingCallsDTOCall.enqueue(new Callback<List<CastingCall>>() {
                @Override
                public void onResponse(Call<List<CastingCall>> call, Response<List<CastingCall>> response) {
                    castingCallsList=response.body();
                    setAdapterToView(castingCallsList);

                }




                @Override
                public void onFailure(Call<List<CastingCall>> call, Throwable t) {


                }
            });
        }


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserCastingCallsActivity.this,CreateUserCastingCallsActivity.class);
                intent.putExtra("isNew",true);
                startActivity(intent);
            }
        });

        castingCalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CastingCall currentCastingCall=castingCallsList.get(position);
                SessionInfo.getInstance().setCurrentCastingCall(currentCastingCall);
                Intent intent=new Intent(UserCastingCallsActivity.this,ApplyCastingCallActivity.class);
                startActivity(intent);

            }
        });





    }
    public void setAdapterToView(List<CastingCall> castingCallList)
    {
        if(castingCallList==null){
            castingCallHeader.setVisibility(View.INVISIBLE);


        }
        else {
            adapter = new CastingCallsAdapter(UserCastingCallsActivity.this, castingCallList);
            castingCalls.setAdapter(adapter);
        }

    }
}
