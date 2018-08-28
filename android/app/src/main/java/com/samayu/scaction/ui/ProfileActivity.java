package com.samayu.scaction.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.scaction.R;
import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SCAClient;

import com.samayu.scaction.service.SessionInfo;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends SCABaseActivity {

    EditText screenName,name,emailAddress,phoneNumber,whatsappNumber,location;
    Button register,reset;
    CheckBox searchable;
    Spinner country;
    Context context;
    View focusView=null;
    boolean valid=false;




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
        location = (EditText) findViewById(R.id.location);
        country=(Spinner) findViewById(R.id.editCountry);
        searchable=(CheckBox) findViewById(R.id.searchable);


        FBUserDetails fbUserDetails= SessionInfo.getInstance().getFbUserDetails();
        if(fbUserDetails !=null){
            name.setText(fbUserDetails.getName());
            emailAddress.setText(fbUserDetails.getEmailAddress());
        }

        List<Country> countryList= SessionInfo.getInstance().getCountries();
        final ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(ProfileActivity.this, R.layout.drop_down_list, countryList);
        country.setAdapter(adapter);
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


    }



    public void attemptRegister(){
        //String customerId=null;
        String screenName1=screenName.getText().toString();
        String name1=name.getText().toString();
        String email=emailAddress.getText().toString();

        String phone=phoneNumber.getText().toString();
        String whatsapp=phoneNumber.getText().toString();
        String city1=location.getText().toString();

        Boolean isSearchable;
        int ctry = country.getSelectedItemPosition();
        Country selectedCountry = (Country) country.getSelectedItem();
        String selectedCountryCode = selectedCountry.getCode();

        if(searchable.isChecked()){
            isSearchable=true;
        }
        else{
            isSearchable=false;
        }
        boolean validation=  validation(screenName1,name1,email,phone,whatsapp,city1,ctry);
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
            createUser.setCity(city1);
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
    public boolean validation(String screenName1,String name1,String email,String phone,String whatsapp,String city1,int ctry){
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

        if(TextUtils.isEmpty(city1)){
            location.setError("This Field is Required");
            focusView = location;
            valid = true;
            return  valid;
        }


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
        location.setText("");

        country.setSelection(0);
    }


}
