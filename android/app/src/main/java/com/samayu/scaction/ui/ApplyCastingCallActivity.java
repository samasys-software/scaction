package com.samayu.scaction.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.CastingCallApplication;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.DateFormatter;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.Listeners.RecyclerListener;
import com.samayu.scaction.ui.adapter.CastingCallApplicationsAdapter;
import com.samayu.scaction.ui.adapter.RolesAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyCastingCallActivity extends SCABaseActivity {

    Button registerToApply,apply,unapply,loginAndRegister;
    ImageButton edit;
    Context context;
    User user;
    TextView projectName,projectDetails,productionCompany,eventDate,hours,address,cityAndCountry,castingCallCount;
    RecyclerView castingCallApplicationsView;
    RecyclerView castingcallsRoles;
    View focusView=null;
    boolean valid=false;
    String country1="";
    RolesAdapter profileAdapter;
    int currentUser;
    List<CastingCallApplication> castingCallApplicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_casting_call);
        context=this;
        LinearLayoutManager castingcallsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager castingcallsApplicationsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        registerToApply=(Button) findViewById(R.id.registerToApply);
        apply=(Button) findViewById(R.id.apply);
        loginAndRegister=(Button) findViewById(R.id.loginAndRegister);
        unapply=(Button) findViewById(R.id.unApply);
        edit=(ImageButton) findViewById(R.id.edit);

        projectName=(TextView) findViewById(R.id.displayProjectName);
       // projectDetails=(TextView) findViewById(R.id.displayProjectDetails);
        productionCompany=(TextView) findViewById(R.id.displayProductionCompany);
        eventDate=(TextView) findViewById(R.id.displayDate);
        hours=(TextView) findViewById(R.id.displayHours);
        address=(TextView) findViewById(R.id.displayAddress);
        cityAndCountry=(TextView) findViewById(R.id.displayCityAndCountry);
        castingCallCount=(TextView) findViewById(R.id.castingCallCount);

        castingCallApplicationsView=(RecyclerView) findViewById(R.id.listOfCastingCallsApplications);
        castingCallApplicationsView.setLayoutManager(castingcallsApplicationsLayoutManager);


         castingcallsRoles=(RecyclerView) findViewById(R.id.castingCallRoleList) ;
        castingcallsRoles.setLayoutManager(castingcallsLayoutManager);



        List<ProfileType> profileTypes=SessionInfo.getInstance().getProfileTypes();

      //  List<ProfileType> castingCallProfileTypes=new ArrayList<ProfileType>();

        List<SelectedCastingCallRoles> selectedCastingCallRolesList=new ArrayList<SelectedCastingCallRoles>();

        // role=(TextView) findViewById(R.id.displayRole);

        final CastingCall currentCastingCall= SessionInfo.getInstance().getCurrentCastingCall();
        if(currentCastingCall!=null){
            projectName.setText(currentCastingCall.getProjectName());
           // projectDetails.setText(currentCastingCall.getProjectDetails());

            productionCompany.setText(currentCastingCall.getProductionCompany());
            eventDate.setText(DateFormatter.getMonthDateYearFormat(String.valueOf(currentCastingCall.getStartDate()))+" to "+DateFormatter.getMonthDateYearFormat(String.valueOf(currentCastingCall.getEndDate())));




            hours.setText(currentCastingCall.getHours());
            address.setText(currentCastingCall.getAddress());

            String countryCode="";

            List<Country> countries=SessionInfo.getInstance().getCountries();
            for(Country country:countries){
                if(country.getId()==currentCastingCall.getCountryId()){
                    country1=country.getName();
                    countryCode=country.getCode();
                    break;
                }
            }

            Call<List<City>> cityDTOCall = new SCAClient().getClient().getCities(countryCode);
            cityDTOCall.enqueue(new Callback<List<City>>() {
                @Override
                public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                    List<City> cityList = response.body();

                    String city1="";


                    if(cityList!=null) {


                        City defaultCity = new City();
                        defaultCity.setId(0);
                        defaultCity.setName("Select City");
                        cityList.add(0, defaultCity);

                        SessionInfo.getInstance().setCities(cityList);

                        for (City city : cityList) {
                            if (city.getId() == currentCastingCall.getCityId()) {
                                city1 = city.getName();
                                break;
                            }
                        }

                        cityAndCountry.setText(city1 +" , "+country1);





                    }


                }

                @Override
                public void onFailure(Call<List<City>> call, Throwable t) {

                }


            });




//            String country1="";
//            List<Country> countries=SessionInfo.getInstance().getCountries();
//            for(Country country:countries){
//                if(country.getId()==currentCastingCall.getCountryId()){
//                    country1=country.getName();
//                    break;
//                }
//            }



            String[] arrayOfId = currentCastingCall.getRoleIds().split(",");

            int[] roleIdList=new int[arrayOfId.length];


            for(int i=0;i<arrayOfId.length;i++ ){
                roleIdList[i]=Integer.parseInt(arrayOfId[i]);
                System.out.println(i+"th Id"+roleIdList[i]);
            }



            for(int i=0;i<roleIdList.length;i++)
            {
                for(int j=0;j<profileTypes.size();j++)
                {
                    if(profileTypes.get(j).getId()==roleIdList[i]){
                        ProfileType profileType=new ProfileType();
                        profileType.setId(profileTypes.get(j).getId());
                        profileType.setName(profileTypes.get(j).getName());
                        //castingCallProfileTypes.add(profileType);

                        SelectedCastingCallRoles selectedCastingCallRoles=new SelectedCastingCallRoles();
                        selectedCastingCallRoles.setChecked(false);
                        selectedCastingCallRoles.setProfileType(profileType);



                        selectedCastingCallRolesList.add(selectedCastingCallRoles);

                    }
                }
            }

            SessionInfo.getInstance().setSelectedCastingCallRoles(selectedCastingCallRolesList);





//           x role.setText(currentCastingCall.getRoleDetails());
        }

        user=SessionInfo.getInstance().getUser();
        if(SessionInfo.getInstance().getFbUserDetails()!=null) {
            if (user == null) {
                registerToApply.setVisibility(View.VISIBLE);
                currentUser=0;
            } else {
                if (currentCastingCall.getUserId() == user.getUserId()) {
                    edit.setVisibility(View.VISIBLE);
                    currentUser=1;

                    Call<List<CastingCallApplication>> getUserCastingCallApplicationsDTOCall = new SCAClient().getClient().getCastingCallApplications(currentCastingCall.getId());
                    getUserCastingCallApplicationsDTOCall.enqueue(new Callback<List<CastingCallApplication>>() {
                        @Override
                        public void onResponse(Call<List<CastingCallApplication>> call, Response<List<CastingCallApplication>> response) {

                             castingCallApplicationList = response.body();
                            if (castingCallApplicationList.size()>0) {
                                castingCallCount.setVisibility(View.VISIBLE);
                                castingCallCount.setText(getResources().getString(R.string.casting_call_applications)+"("+castingCallApplicationList.size()+")");
                                CastingCallApplicationsAdapter adapter = new CastingCallApplicationsAdapter(ApplyCastingCallActivity.this, castingCallApplicationList);
                                castingCallApplicationsView.setAdapter(adapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<CastingCallApplication>> call, Throwable t) {


                        }
                    });

                } else {
                    currentUser=2;
                    Call<CastingCall> getCastingCallDTOCall = new SCAClient().getClient().getCastingCall(currentCastingCall.getId(), user.getUserId());
                    System.out.println(user.getUserId());
                    getCastingCallDTOCall.enqueue(new Callback<CastingCall>() {
                        @Override
                        public void onResponse(Call<CastingCall> call, Response<CastingCall> response) {

                            CastingCall castingCall = response.body();
                            List<CastingCallApplication> castingCallApplicationList = castingCall.getUserApplications();
                            if (castingCallApplicationList == null) {
                                apply.setVisibility(View.VISIBLE);
                            } else {
                                for (CastingCallApplication currentCastingCallApplication : castingCallApplicationList) {
                                    if (currentCastingCallApplication.getUser().getUserId() == user.getUserId()) {
                                        unapply.setVisibility(View.VISIBLE);
                                        break;
                                    } else {
                                        apply.setVisibility(View.VISIBLE);
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CastingCall> call, Throwable t) {
                        }
                    });
                     // apply.setVisibility(View.VISIBLE);
                }
            }
        }
        else
        {
            loginAndRegister.setVisibility(View.VISIBLE);
            currentUser=0;
        }


        profileAdapter= new RolesAdapter(this,1,currentUser,user);
       // listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        castingcallsRoles.setAdapter(profileAdapter);
        registerToApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ApplyCastingCallActivity.this,ProfileActivity.class);
                intent.putExtra("isNew",true);
                startActivity(intent);
            }
        });

        loginAndRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ApplyCastingCallActivity.this,UseFacebookLoginActivity.class);
                startActivity(intent);
            }
        });

        castingCallApplicationsView.addOnItemTouchListener(new RecyclerListener(context,
                castingCallApplicationsView, new RecyclerListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                long userId=castingCallApplicationList.get(position).getUser().getUserId();
                Intent intent=new Intent(ApplyCastingCallActivity.this,ViewPortfolioActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

                //here your logic
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

//        castingCallApplicationsView.setOnClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });



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

//                SparseBooleanArray checked = listView.getCheckedItemPositions();
//                int selectedRoleId=0;
//                for (int i = 0; i < checked.size(); i++) {
//                    // Item position in adapter
//                    int position = checked.keyAt(i);
//                    // Add sport if it is checked i.e.) == TRUE!
//                    if (checked.valueAt(i))
//                        selectedRoleId=profileAdapter.getItem(position).getId();
//                }
            for(SelectedCastingCallRoles selectedCastingCallRoles:SessionInfo.getInstance().getSelectedCastingCallRoles()){
                if(selectedCastingCallRoles.isChecked()){
                applyCastingCall(currentCastingCall,user.getUserId(),selectedCastingCallRoles.getProfileType());
                }

            }
            }
        });
       // listView.setOnContextClickListener();
    }

    private void applyCastingCall(final CastingCall currentCastingCall, long userId, final ProfileType profileType){
        Call<Boolean> applyCastingCallDTOCall = new SCAClient().getClient().applyCastingCall(currentCastingCall.getId(),userId,profileType.getId());
        applyCastingCallDTOCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.i("Success","kkHai");
                Toast.makeText(context,"You Have Succesfully Applied For "+ profileType.getName()+" with "+currentCastingCall.getProjectName(),Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }
}
