package com.samayu.scaction.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

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
    ArrayAdapter<String> adapter;

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
        adapter= new ArrayAdapter<String>(this,
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
                final ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(ProfileActivity.this, R.layout.drop_down_list, countryList);
                country.setAdapter(countryAdapter);



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

                        final ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(ProfileActivity.this, R.layout.drop_down_list, cityList);
                        city.setAdapter(cityAdapter);



                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable t) {

                    }


                });

                    AsyncTask task = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                            String locationProvider = LocationManager.GPS_PROVIDER;
                            String fullAddress = "";
                            if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                fullAddress = "No Permission available";
                                ActivityCompat.requestPermissions(ProfileActivity.this, new String[] {
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION },
                                        1);
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                            }
                            {

                                try {
                                    Looper.prepare();
                                    manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                                        @Override
                                        public void onLocationChanged(Location location) {
                                            System.out.println("Location Changed ");
                                        }

                                        @Override
                                        public void onStatusChanged(String s, int i, Bundle bundle) {
                                            System.out.println("Location Status Changed ");
                                        }

                                        @Override
                                        public void onProviderEnabled(String s) {
                                            System.out.println("Provider Enabled ");
                                        }

                                        @Override
                                        public void onProviderDisabled(String s) {
                                            System.out.println("Provider Disabled");
                                        }
                                    }, null);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                Location currentLocation = manager.getLastKnownLocation(locationProvider);
                                if(currentLocation==null){
                                    return "No Permission Available";
                                }
                                Geocoder coder = new Geocoder(ProfileActivity.this);
                                try {
                                    List<Address> addresses = coder.getFromLocation(currentLocation.getLatitude() , currentLocation.getLongitude() , 20 );
                                    //List<Address> addresses = coder.getFromLocation(10.828547,78.666821,20);
                                    if(addresses.size()>3){
                                        Address address = addresses.get(0);

                                        return address;
                                    }



                                } catch (IOException e) {
                                    fullAddress+="Error "+e;
                                    e.printStackTrace();
                                }

                            }
                            return fullAddress;
                        }
                    };

                    task.execute((Object[])null);
                    Address address = null;
                    try {
                        Object result = task.get();
                        if( result instanceof  Address ){
                            address = (Address) result;

                            String  address1 = address.getAddressLine(0);
                            String address2 = address.getAddressLine(1);
                            String address3 = address.getAddressLine(2);
                            String[] address1Array = address1.split(" ");

                            StringTokenizer st = new StringTokenizer(address2,", ");
                            String[] address2Array = address2.split(", ");



                            ArrayAdapter<Country> ctryadapter =(ArrayAdapter<Country>) country.getAdapter();
                            int ctryCount =  ctryadapter.getCount();
                            for(int i = 0; i<ctryCount; i++){
                                Country aCountry= ctryadapter.getItem(i);
                                if(aCountry.getName().equalsIgnoreCase(address3)){
                                    country.setSelection(i);
                                    break;
                                }
                            }

                            ArrayAdapter<City> cityadapter =(ArrayAdapter<City>) city.getAdapter();
                            int count =  cityadapter.getCount();
                            for(int i = 0; i<count; i++){
                                City aCity= cityadapter.getItem(i);
                                if(aCity.getName().equalsIgnoreCase(st.nextToken())){
                                    city.setSelection(i);
                                    break;
                                }
                            }
                        }
                        else if( result instanceof  String ) {
                            String errorText = (String) task.get();
                            //TODO UnitNumber setError.
                        }
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    } catch (ExecutionException e) {

                        e.printStackTrace();
                    }










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

                String date1 = syear +"-" + formattedMonth1  + "-" +formattedDayOfMonth1;
                dob.setText(date1);
            }
        };




    }



    public void attemptRegister(){

//        Call<String> registerDTOCall = new SCAClient().getClient().sample(9);
//        registerDTOCall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                t.printStackTrace();
//
//            }
//        });
        //String customerId=null;
        String screenName1=screenName.getText().toString();
        String name1=name.getText().toString();
        String email=emailAddress.getText().toString();

        String phone=phoneNumber.getText().toString();
        String whatsapp=phoneNumber.getText().toString();
        int selectedCityPosition= city.getSelectedItemPosition();
        City selectedCity = (City) city.getSelectedItem();
        String selectedCityName = selectedCity.getName();

        String isSearchable;
        int ctry = country.getSelectedItemPosition();
        Country selectedCountry = (Country) country.getSelectedItem();
        String selectedCountryCode = selectedCountry.getCode();

        String gender1;

        int whichIndex = gender.getCheckedRadioButtonId();
        if(whichIndex==R.id.gender_male)
        {
            gender1="0";
        }
        else{
            gender1="1";
        }


        if(searchable.isChecked()){
            isSearchable="true";
        }
        else{
            isSearchable="false";
        }

        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] rolesList = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            rolesList[i] = selectedItems.get(i);
        }



        boolean validation=  validation(screenName1,name1,email,phone,whatsapp,selectedCityPosition,ctry);
        if (validation) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return;
        }
       else {

            try {
                String fbUser="dwde"; //SessionInfo.getInstance().getFbUserDetails().getId();
                String url="cfe";//SessionInfo.getInstance().getFbUserDetails().getId()

                Call<User> registerDTOCall = new SCAClient().getClient().registerNewUser(fbUser, screenName1, name1, email, selectedCountryCode, selectedCityName, phone, whatsapp, gender1, dob.getText().toString(), isSearchable,url, rolesList);
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
            catch (Exception e){
                e.printStackTrace();
            }
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
