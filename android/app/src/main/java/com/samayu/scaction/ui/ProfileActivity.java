package com.samayu.scaction.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SCAClient;

import com.samayu.scaction.service.SessionInfo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends SCABaseActivity {

    EditText screenName,name,emailAddress,phoneNumber,whatsappNumber;
    TextView dob;
    Button register,reset;
    CheckBox searchable;
    RadioGroup gender;
    Spinner country,city;
    Context context;
    ListView listView;
    View focusView=null;
    boolean valid=false;

    private ImageButton dateOfBirthPicker;


    private DatePickerDialog.OnDateSetListener mDateSetListener1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;

        //String[] countries = { "IN", "USA"  };
        register = (Button) findViewById(R.id.register);
        reset = (Button) findViewById(R.id.reset);
        screenName = (EditText) findViewById(R.id.screenName);
        name = (EditText) findViewById(R.id.name);
        emailAddress = (EditText) findViewById(R.id.emailAddress);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        whatsappNumber = (EditText) findViewById(R.id.whatsappNumber);
        city = (Spinner) findViewById(R.id.city);
        country=(Spinner) findViewById(R.id.editCountry);

        searchable=(CheckBox) findViewById(R.id.searchable);


         gender = (RadioGroup) findViewById(R.id.gender);
         listView=(ListView) findViewById(R.id.roleList) ;

        dob=(TextView)findViewById(R.id.startDate);


        dateOfBirthPicker=(ImageButton)findViewById(R.id.startDatePicker);

        String[] roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, roles);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);








        FBUserDetails fbUserDetails= SessionInfo.getInstance().getFbUserDetails();
        if(fbUserDetails !=null){
            name.setText(fbUserDetails.getName());
            emailAddress.setText(fbUserDetails.getEmailAddress());
        }



        Call<List<Country>> countryDTOCall = new SCAClient().getClient().getCountries();
        countryDTOCall.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {

                    List<Country> countryDTO = response.body();




                    Country defaultCountry = new Country();
                    defaultCountry.setId(0);
                    defaultCountry.setName("Select Country");
                    countryDTO.add(0, defaultCountry  );

                    SessionInfo.getInstance().setCountries(countryDTO);
                    List<Country> countryList=SessionInfo.getInstance().getCountries();
                final ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(ProfileActivity.this, R.layout.drop_down_list, countryList);
                country.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }


        });

        //List<Country> countryList= SessionInfo.getInstance().getCountries();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });


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




                        City defaultCity = new City();
                        defaultCity.setId(0);
                        defaultCity.setName("Select Country");
                        cityList.add(0, defaultCity  );

                        SessionInfo.getInstance().setCities(cityList);

                        final ArrayAdapter<City> adapter = new ArrayAdapter<City>(ProfileActivity.this, R.layout.drop_down_list, cityList);
                        city.setAdapter(adapter);



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

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id=group.getCheckedRadioButtonId();

                RadioButton rb=(RadioButton) findViewById(checkedId);

                String radioText=rb.getText().toString();
                Log.i("gender",radioText);

            }
        });

        dateOfBirthPicker.setOnClickListener(new View.OnClickListener()
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
                        mDateSetListener1,
                        year1,month1,day1);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday)
            {

                int month1 = smonth + 1;
                String formattedMonth1 = "" + month1;
                String formattedDayOfMonth1 = "" + sday;

                if(month1 < 10)
                {
                    formattedMonth1 = "0" + month1;
                }
                if(sday < 10)
                {
                    formattedDayOfMonth1 = "0" + sday;
                }

                String date1 = formattedDayOfMonth1 + "/" + formattedMonth1 + "/" + syear;
                dob.setText(date1);
            }
        };




    }



    public void attemptRegister(){
        //String customerId=null;
        String screenName1=screenName.getText().toString();
        String name1=name.getText().toString();
        String email=emailAddress.getText().toString();

        String phone=phoneNumber.getText().toString();
        String whatsapp=phoneNumber.getText().toString();
        int selectedCityPosition= city.getSelectedItemPosition();
        City selectedCity = (City) city.getSelectedItem();
        String selectedCityName = selectedCity.getName();

        Boolean isSearchable;
        int ctry = country.getSelectedItemPosition();
        Country selectedCountry = (Country) country.getSelectedItem();
        String selectedCountryCode = selectedCountry.getCode();
        int selectedId = gender.getCheckedRadioButtonId();


        if(searchable.isChecked()){
            isSearchable=true;
        }
        else{
            isSearchable=false;
        }
        boolean validation=  validation(screenName1,name1,email,phone,whatsapp,selectedCityPosition,ctry);
        if (validation) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return;
        }
       else {

            CreateUser createUser=new CreateUser();
            createUser.setFbUser(SessionInfo.getInstance().getFbUserDetails().getId());
            createUser.setFbEmail(email);
            createUser.setCity(selectedCityName);
            createUser.setFbName(name1);
            createUser.setScreenName(screenName1);
            createUser.setPhoneNumber(phone);
            createUser.setWhatsappNumber(whatsapp);
            createUser.setSearchable(String.valueOf(isSearchable));
            createUser.setCountryCode(selectedCountryCode);
            createUser.setGender("");
            createUser.setDateOfBirth("");
            createUser.setRoles(new String[]{""});

            Call<User> registerDTOCall = new SCAClient().getClient().registerNewUser(createUser);
            registerDTOCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {


                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }
    }
    public boolean validation(String screenName1,String name1,String email,String phone,String whatsapp,int city1,int ctry){
        //  public boolean validation(){

        // Reset errors.
        valid = false;

        if(TextUtils.isEmpty(screenName1)){
            screenName.setError("This Field is Required");
            focusView = screenName;
            valid = true;
            return  valid;
        }
        if(TextUtils.isEmpty(name1)){
            name.setError("This Field is Required");
            focusView = name;
            valid = true;
            return  valid;
        }
        if(TextUtils.isEmpty(email)){
            emailAddress.setError("This Field is Required");
            focusView = emailAddress;
            valid = true;
            return  valid;
        }
       /* if (!isValidEmail(email)) {
            emailAddress.setError("Invalid Email");
            valid = true;
            return  valid;
        }*/



        if(TextUtils.isEmpty(phone)){
            phoneNumber.setError("This Field is Required");
            focusView = phoneNumber;
            valid = true;
            return  valid;
        }

        if(TextUtils.isEmpty(whatsapp)){
            phoneNumber.setError("This Field is Required");
            focusView = whatsappNumber;
            valid = true;
            return  valid;
        }

        if (ctry<= 0) {
            //   country.setError("This Field is Required");
            TextView errorText = (TextView)country.getSelectedView();
            errorText.setError(getString(R.string.error_field_required));
            Toast.makeText(this, "This field is Reqiured", Toast.LENGTH_SHORT).show();
            focusView=errorText;
            valid = true;
            return  valid;
        }

        if (city1<= 0) {
            //   country.setError("This Field is Required");
            TextView errorText = (TextView)city.getSelectedView();
            errorText.setError(getString(R.string.error_field_required));
            Toast.makeText(this, "This field is Reqiured", Toast.LENGTH_SHORT).show();
            focusView=errorText;
            valid = true;
            return  valid;
        }
        return valid;
    }

    // validating email id
   /* private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }*/
    public void reset(){
        screenName.setText("");

        phoneNumber.setText("");
        whatsappNumber.setText("");
        city.setSelection(0);

        country.setSelection(0);
    }



}
