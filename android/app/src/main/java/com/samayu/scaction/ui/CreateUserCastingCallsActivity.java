package com.samayu.scaction.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.DateFormatter;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.adapter.RolesAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class CreateUserCastingCallsActivity extends SCABaseActivity {
    private DatePickerDialog.OnDateSetListener startDateSetListener,endDateSetListener;

    ImageButton startdatePicker,endDatePicker;

    TextView startDate,endDate;

    Button castingCallCreate;

    EditText projectName,projectDetails,productionCompany,role,startAge,endAge,address,hours;

    Spinner country,city;

    RadioGroup gender;

    RecyclerView rolesList;


    View focusView=null;
    boolean valid=false;

    Context context;

    ArrayAdapter<ProfileType> profileAdapter;

    long castingCallId;

    String message;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_casting_calls);
        context=this;
        progressDialog=getProgressDialog(context);

        LinearLayoutManager addRoleListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        startDate=(TextView) findViewById(R.id.addStartDate);
        endDate=(TextView) findViewById(R.id.addEndDate);
        startdatePicker=(ImageButton) findViewById(R.id.addStartDatePicker);
        endDatePicker=(ImageButton) findViewById(R.id.addEndDatePicker);
        castingCallCreate=(Button) findViewById(R.id.castingCallCreate);
       // castingCallReset=(Button) findViewById(R.id.castingCallreset);
        projectName=(EditText) findViewById(R.id.addProjectName);
        projectDetails=(EditText) findViewById(R.id.addProjectdetails);
        productionCompany=(EditText) findViewById(R.id.addProductionCompany);
        role=(EditText) findViewById(R.id.addRole);
        startAge=(EditText) findViewById(R.id.addStartAge);
        endAge=(EditText) findViewById(R.id.addEndAge);
        address=(EditText) findViewById(R.id.addAddress);
        hours=(EditText) findViewById(R.id.addHours);
        city = (Spinner) findViewById(R.id.addCity);
        country=(Spinner) findViewById(R.id.addCountry);
        gender = (RadioGroup) findViewById(R.id.addGender);
        rolesList=(RecyclerView) findViewById(R.id.addRoleList);
        rolesList.setLayoutManager(addRoleListLayoutManager);
        final CastingCall currentCastingCall;

        List<Country> countryList= SessionInfo.getInstance().getCountries();
        ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(CreateUserCastingCallsActivity.this, R.layout.drop_down_list, countryList);
        country.setAdapter(countryAdapter);
        country.setSelection(0);

        List<City> cities=new ArrayList<City>();
        City defaultCity = new City();
        defaultCity.setId(0);
        defaultCity.setName("Select City");
        cities.add(0, defaultCity);
        final ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(CreateUserCastingCallsActivity.this, R.layout.drop_down_list, cities);
        city.setAdapter(cityAdapter);
        city.setSelection(0);

        List<ProfileType> profileTypes=SessionInfo.getInstance().getProfileTypes();

        setRolesInRecyclerView(profileTypes);


        RolesAdapter adapter=new RolesAdapter(CreateUserCastingCallsActivity.this,0,0,null);
        rolesList.setAdapter(adapter);

//        profileAdapter= new ArrayAdapter<ProfileType>(this,
//                android.R.layout.simple_list_item_multiple_choice, profileTypes);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        listView.setAdapter(profileAdapter);

        boolean isNew=getIntent().getExtras().getBoolean("isNew");

        if(isNew){
            castingCallId=-1;
            message="You Have Created A New Casting Call With Start,Camera,Action Successfully!";
            currentCastingCall=null;
        }
        else{
            currentCastingCall=SessionInfo.getInstance().getCurrentCastingCall();
            Log.i("currentCastingCall",currentCastingCall.getRoleIds());
            castingCallId=currentCastingCall.getId();
            message="You Have Edited A Casting Call With Start,Camera,Action Successfully!";
            projectName.setText(currentCastingCall.getProjectName());
            projectDetails.setText(currentCastingCall.getProjectDetails());
            productionCompany.setText(currentCastingCall.getProductionCompany());
            role.setText(currentCastingCall.getRoleDetails());
            startAge.setText(String.valueOf(currentCastingCall.getStartAge()));
            endAge.setText(String.valueOf(currentCastingCall.getEndAge()));
            startDate.setText(DateFormatter.getMonthDateYearFormat(String.valueOf(currentCastingCall.getStartDate())));
            endDate.setText(DateFormatter.getMonthDateYearFormat(String.valueOf(currentCastingCall.getEndDate())));
            address.setText(String.valueOf(currentCastingCall.getAddress()));
            hours.setText(String.valueOf(currentCastingCall.getHours()));
            List<Country> countries=SessionInfo.getInstance().getCountries();
            for(int i=0;i<countries.size();i++){
                if(countries.get(i).getId()==currentCastingCall.getCountryId()){
                    country.setSelection(i);
                    break;

                }

            }

            int gender1=currentCastingCall.getGender();
            if(gender1==0)
                gender.check(R.id.male);

            else if(gender1==1)
                gender.check(R.id.female);
            else if(gender1==2)
                gender.check(R.id.other);

            String[] arrayOfId = currentCastingCall.getRoleIds().split(",");

            int[] roleIdList=new int[arrayOfId.length];


            for(int i=0;i<arrayOfId.length;i++ ){
                roleIdList[i]=Integer.parseInt(arrayOfId[i]);
                System.out.println(i+"th Id"+roleIdList[i]);
            }


            List<SelectedCastingCallRoles> selectedCastingCallRoles=SessionInfo.getInstance().getRolesList();

            for (int i = 0; i < roleIdList.length; i++) {
                for (int j = 0; j < selectedCastingCallRoles.size(); j++) {
                    if (selectedCastingCallRoles.get(j).getProfileType().getId() == roleIdList[i]) {
                        selectedCastingCallRoles.get(j).setChecked(true);
                        //listView.setItemChecked(j, true);
                    }
                }
            }
//            for(int i=0;i<roleIdList.length;i++)
//            {
//                for(int j=0;j<profileTypes.size();j++)
//                {
//                    if(profileTypes.get(j).getId()==roleIdList[i]){
//                        listView.setItemChecked(j,true);
//                    }
//                }
//            }

        }


        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int ctry = country.getSelectedItemPosition();
                Country selectedCountry = (Country) country.getSelectedItem();
                String selectedCountryId = selectedCountry.getCode();

                Call<List<City>> cityDTOCall = new SCAClient().getClient().getCities(selectedCountryId);
                cityDTOCall.enqueue(new Callback<List<City>>() {
                    @Override
                    public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                        List<City> cityList = response.body();


                        if(cityList!=null) {

                            City defaultCity = new City();
                            defaultCity.setId(0);
                            defaultCity.setName("Select City");
                            cityList.add(0, defaultCity);

                            SessionInfo.getInstance().setCities(cityList);

                            final ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(CreateUserCastingCallsActivity.this, R.layout.drop_down_list, cityList);
                            city.setAdapter(cityAdapter);
                            city.setSelection(0);

                            if(currentCastingCall!=null) {

                                for (int i = 0; i < cityList.size(); i++) {
                                    if (cityList.get(i).getId() == currentCastingCall.getCityId()) {
                                        city.setSelection(i);
                                        break;
                                    }

                                }
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable t) {

                    }


                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


//        castingCallReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetCastingCall();
//            }
//        });

        castingCallCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCastingCall();
            }
        });

        startdatePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal1 = Calendar.getInstance();
                int year1 = cal1.get(Calendar.YEAR);
                int month1 = cal1.get(Calendar.MONTH);
                int day1 = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        year1,month1,day1);

                dialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday)
            {
                //startDateFormat=DateFormatter.getYearMonthDateFormat(syear,smonth,sday);
                startDate.setText(DateFormatter.getMonthDateYearFormat(syear,smonth,sday));
            }
        };

        endDatePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal1 = Calendar.getInstance();
                int year1 = cal1.get(Calendar.YEAR);
                int month1 = cal1.get(Calendar.MONTH);
                int day1 = cal1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        year1,month1,day1);
                dialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday)
            {

                //endDateFormat=DateFormatter.getYearMonthDateFormat(syear,smonth,sday);
                endDate.setText(DateFormatter.getMonthDateYearFormat(syear,smonth,sday));
            }
        };
    }



    public void createCastingCall(){
        String projectName1=projectName.getText().toString();
        String projectDetails1=projectDetails.getText().toString();
        String productionCompany1=productionCompany.getText().toString();
        String role1=role.getText().toString();
        String startAge1=startAge.getText().toString();
        String endAge1=endAge.getText().toString();




        int selectedCityPosition= city.getSelectedItemPosition();


        int ctry = country.getSelectedItemPosition();


//        int gender1="";
        int gender1=0;

        int whichIndex=gender.getCheckedRadioButtonId();

        if(whichIndex==R.id.male)
        {
            gender1=0;
        }
        else if(whichIndex==R.id.female){
            gender1=1;
        }
        else if(whichIndex==R.id.other){
            gender1=2;
        }

        String address1=address.getText().toString();
        String startDate1=DateFormatter.getYearMonthDateFormat(startDate.getText().toString());
        String endDate1=DateFormatter.getYearMonthDateFormat(endDate.getText().toString());
        String hours1=hours.getText().toString();

        //  SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<ProfileType> selectedItems = new ArrayList<ProfileType>();
        List<SelectedCastingCallRoles> selectedCastingCallRolesList=SessionInfo.getInstance().getRolesList();
        for (SelectedCastingCallRoles selectedCastingCallRoles:selectedCastingCallRolesList) {
            if(selectedCastingCallRoles.isChecked()){
                selectedItems.add(selectedCastingCallRoles.getProfileType());
            }
//            // Item position in adapter
//            int position = checked.keyAt(i);
//            // Add sport if it is checked i.e.) == TRUE!
//            if (checked.valueAt(i))
//                selectedItems.add(profileAdapter.getItem(position));
        }


        String[] rolesList = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            rolesList[i] = String.valueOf(selectedItems.get(i).getId());
        }

        String[] rolesList1= new String[2];

        for (int i = 0; i < 2; i++) {
            rolesList1[i] = "ACTOR";
        }




        boolean validation=  validation(projectName1,projectDetails1,productionCompany1,role1,startAge1,endAge1,selectedCityPosition,ctry,whichIndex,address1,startDate1,endDate1,hours1);
        if (validation) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return;
        }
        else {

            try {
                int sAge=Integer.parseInt(startAge1);
                int eAge=Integer.parseInt(endAge1);

                City selectedCity = (City) city.getSelectedItem();

                int selectedCityId =selectedCity.getId();


                Country selectedCountry = (Country) country.getSelectedItem();
                int selectedCountryId = selectedCountry.getId();

                progressDialog.show();
                Call<CastingCall> createCastingCallDTOCall = new SCAClient().getClient().createCastingCall(castingCallId,projectName1,projectDetails1,productionCompany1,role1,sAge,eAge,gender1,selectedCityId,selectedCountryId,address1,startDate1,endDate1, hours1,SessionInfo.getInstance().getUser().getUserId(),rolesList);
                createCastingCallDTOCall.enqueue(new Callback<CastingCall>() {
                    @Override
                    public void onResponse(Call<CastingCall> call, Response<CastingCall> response) {
                        if (response.isSuccessful()) {
                            CastingCall castingCall=response.body();
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            //resetCastingCall();
                            Intent intent=new Intent(CreateUserCastingCallsActivity.this,UserCastingCallsActivity.class);
                            intent.putExtra("UserCastingCall",0);
                            startActivity(intent);
                            progressDialog.dismiss();


                        }
                    }

                    @Override
                    public void onFailure(Call<CastingCall> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();

                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public boolean validation(String projectName1,String projectDetails1,String productionCompany1,String role1,String startAge1,String endAge1,int selectedCityPosition,int ctry,int gender1,String address1,String startDate1,String endDate1,String hours1){
        //  public boolean validation(){

        // Reset errors.
        valid = false;

        if(TextUtils.isEmpty(projectName1)){
            projectName.setError("This Field is Required");
            focusView = projectName;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(projectDetails1)){
            projectDetails.setError("This Field is Required");
            focusView = projectDetails;
            valid = true;
            return  valid;
        }
        if(TextUtils.isEmpty(productionCompany1)){
            productionCompany.setError("This Field is Required");
            focusView = productionCompany;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(role1)){
            role.setError("This Field is Required");
            focusView = role;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(startAge1)){
            startAge.setError("This Field is Required");
            focusView = startAge;
            valid = true;
            return  valid;
        }

        int sAge=Integer.parseInt(startAge1);

        if(sAge>100 && sAge<0){
            startAge.setError("The Age Should Between 0 to 100");
            focusView = startAge;
            valid = true;
            return  valid;
        }


        if(TextUtils.isEmpty(endAge1)){
            endAge.setError("This Field is Required");
            focusView = endAge;
            valid = true;
            return  valid;
        }

        int eAge=Integer.parseInt(endAge1);

        if(eAge>100 && eAge<0){
            endAge.setError("The Age Should Between 0 to 100");
            focusView = endAge;
            valid = true;
            return  valid;
        }

       /* if (!isValidEmail(email)) {
            emailAddress.setError("Invalid Email");
            valid = true;
            return  valid;
        }*/


        if (ctry<= 0) {
            //   country.setError("This Field is Required");
            TextView errorText = (TextView)country.getSelectedView();
            errorText.setError(getString(R.string.error_field_required));
            Toast.makeText(this, "This field is Reqiured", Toast.LENGTH_SHORT).show();
            focusView=errorText;
            valid = true;
            return  valid;
        }

        if (selectedCityPosition<= 0) {
            //   country.setError("This Field is Required");
            TextView errorText = (TextView)city.getSelectedView();
            errorText.setError(getString(R.string.error_field_required));
            Toast.makeText(this, "This field is Reqiured", Toast.LENGTH_SHORT).show();
            focusView=errorText;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(address1)){
            address.setError("This Field is Required");
            focusView = address;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(startDate1)){
            startDate.setError("This Field is Required");
            focusView = startDate;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(endDate1)){
            endDate.setError("This Field is Required");
            focusView = endDate;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(hours1)){
            hours.setError("This Field is Required");
            focusView = hours;
            valid = true;
            return  valid;
        }

        if(gender1==-1)
        {
            RadioButton otherButton = (RadioButton) findViewById(R.id.other);
            otherButton.setError("Select Gender");
            focusView=otherButton;
            valid = true;
            return  valid;
        }
        return valid;

    }

//    private void resetCastingCall(){
//        projectName.setText("");
//        projectDetails.setText("");
//        productionCompany.setText("");
//        startAge.setText("");
//        endAge.setText("");
//        address.setText("");
//        startDate.setText("");
//        endDate.setText("");
//        country.setSelection(0);
//        city.setSelection(0);
//        hours.setText("");
//        gender.setSelected(false);
//        listView.setSelected(false);
//
//    }


}
