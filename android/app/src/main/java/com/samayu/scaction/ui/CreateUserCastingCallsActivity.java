package com.samayu.scaction.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class CreateUserCastingCallsActivity extends SCABaseActivity {
    private DatePickerDialog.OnDateSetListener startDateSetListener,endDateSetListener;

    ImageButton startdatePicker,endDatePicker;

    TextView startDate,endDate;

    Button castingCallCreate,castingCallReset;

    EditText projectName,projectDetails,productionCompany,role,startAge,endAge,address,hours;

    Spinner country,city;

    RadioGroup gender;

    ListView listView;


    View focusView=null;
    boolean valid=false;

    Context context;

    ArrayAdapter<ProfileType> profileAdapter;

    long castingCallId;

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_casting_calls);
        context=this;

        startDate=(TextView) findViewById(R.id.addStartDate);
        endDate=(TextView) findViewById(R.id.addEndDate);
        startdatePicker=(ImageButton) findViewById(R.id.addStartDatePicker);
        endDatePicker=(ImageButton) findViewById(R.id.addEndDatePicker);
        castingCallCreate=(Button) findViewById(R.id.castingCallCreate);
        castingCallReset=(Button) findViewById(R.id.castingCallreset);
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
        listView=(ListView) findViewById(R.id.addRoleList);
        final CastingCall currentCastingCall;

        List<Country> countryList= SessionInfo.getInstance().getCountries();
        ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(CreateUserCastingCallsActivity.this, R.layout.drop_down_list, countryList);
        country.setAdapter(countryAdapter);

        List<City> cities=new ArrayList<City>();
        City defaultCity = new City();
        defaultCity.setId(0);
        defaultCity.setName("Select City");
        cities.add(0, defaultCity);
        final ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(CreateUserCastingCallsActivity.this, R.layout.drop_down_list, cities);
        city.setAdapter(cityAdapter);

        List<ProfileType> profileTypes=SessionInfo.getInstance().getProfileTypes();

        profileAdapter= new ArrayAdapter<ProfileType>(this,
                android.R.layout.simple_list_item_multiple_choice, profileTypes);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(profileAdapter);

        boolean isNew=getIntent().getExtras().getBoolean("isNew");

        if(isNew){
            castingCallId=-1;
            message="You Have Created A New Casting Call With Start,Camera,Action Successfully!";
            currentCastingCall=null;
        }
        else{
            currentCastingCall=SessionInfo.getInstance().getCurrentCastingCall();
            castingCallId=currentCastingCall.getId();
            message="You Have Edited A Casting Call With Start,Camera,Action Successfully!";
            projectName.setText(currentCastingCall.getProjectName());
            projectDetails.setText(currentCastingCall.getProjectDetails());
            productionCompany.setText(currentCastingCall.getProductionCompany());
            role.setText(currentCastingCall.getRoleDetails());
            startAge.setText(String.valueOf(currentCastingCall.getStartAge()));
            endAge.setText(String.valueOf(currentCastingCall.getEndAge()));
            startDate.setText(String.valueOf(currentCastingCall.getStartDate()));
            endDate.setText(String.valueOf(currentCastingCall.getEndDate()));
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


            /*List<UserRole> userRoles=(UserRole)currentCastingCall.getRoleIds();

            for(int i=0;i<userRoles.size();i++)
            {
                for(int j=0;j<profileTypes.size();j++)
                {
                    if(profileTypes.get(j).getId()==userRoles.get(i).getRoleType().getId()){
                        listView.setItemChecked(j,true);
                    }
                }
            }*/

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


        castingCallReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCastingCall();
            }
        });

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

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday)
            {
                startDate.setText(dateSetter(syear,smonth,sday));
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

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday)
            {


                endDate.setText(dateSetter(syear,smonth,sday));
            }
        };
    }

    private String dateSetter(int year,int month,int day){
        int month1 = month + 1;
        String formattedMonth1 = "" + month1;
        String formattedDayOfMonth1 = "" + day;

        if(month1 < 10)
        {
            formattedMonth1 = "0" + month1;
        }
        if(day < 10)
        {
            formattedDayOfMonth1 = "0" + day;
        }

        String date = year +"-" + formattedMonth1  + "-" +formattedDayOfMonth1;
        return date;
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

        int gender1=gender.getCheckedRadioButtonId();

//        if(whichIndex==R.id.male)
//        {
//            gender1="0";
//        }
//        else if(whichIndex==R.id.female){
//            gender1="1";
//        }
//        else if(whichIndex==R.id.other){
//            gender1="2";
//        }

        String address1=address.getText().toString();
        String startDate1=startDate.getText().toString();
        String endDate1=endDate.getText().toString();
        String hours1=hours.getText().toString();

        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<ProfileType> selectedItems = new ArrayList<ProfileType>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(profileAdapter.getItem(position));
        }

        String[] rolesList = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            rolesList[i] = String.valueOf(selectedItems.get(i).getId());
        }

        String[] rolesList1= new String[2];

        for (int i = 0; i < 2; i++) {
            rolesList1[i] = "ACTOR";
        }




        boolean validation=  validation(projectName1,projectDetails1,productionCompany1,role1,startAge1,endAge1,selectedCityPosition,ctry,gender1,address1,startDate1,endDate1,hours1);
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


                Call<CastingCall> createCastingCallDTOCall = new SCAClient().getClient().createCastingCall(castingCallId,projectName1,projectDetails1,productionCompany1,role1,sAge,eAge,gender1,selectedCityId,selectedCountryId,address1,startDate1,endDate1, hours1,SessionInfo.getInstance().getUser().getUserId(),rolesList);
                createCastingCallDTOCall.enqueue(new Callback<CastingCall>() {
                    @Override
                    public void onResponse(Call<CastingCall> call, Response<CastingCall> response) {
                        if (response.isSuccessful()) {
                            CastingCall castingCall=response.body();
                            Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            resetCastingCall();
                            Intent intent=new Intent(CreateUserCastingCallsActivity.this,UserCastingCallsActivity.class);
                            intent.putExtra("UserCastingCall",true);
                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onFailure(Call<CastingCall> call, Throwable t) {
                        t.printStackTrace();

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

    private void resetCastingCall(){
        projectName.setText("");
        projectDetails.setText("");
        productionCompany.setText("");
        startAge.setText("");
        endAge.setText("");
        address.setText("");
        startDate.setText("");
        endDate.setText("");
        country.setSelection(0);
        city.setSelection(0);
        hours.setText("");
        gender.setSelected(false);
        listView.setSelected(false);

    }
}
