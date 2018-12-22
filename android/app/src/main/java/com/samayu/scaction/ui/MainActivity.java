package com.samayu.scaction.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.ProfileDefaults;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SCABaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<ProfileDefaults> profileDefaultsCall = new SCAClient().getClient().getProfileDefaults();
        profileDefaultsCall.enqueue(new Callback<ProfileDefaults>() {
            @Override
            public void onResponse(Call<ProfileDefaults> call, Response<ProfileDefaults> response) {

                ProfileDefaults profileDefaults = response.body();

                List<Country> countries=profileDefaults.getCountries();

                Country defaultCountry = new Country();
                defaultCountry.setId(0);
                defaultCountry.setIsdCode("");
                defaultCountry.setCode("");
                defaultCountry.setName("Select Country");
                countries.add(0, defaultCountry  );

                SessionInfo.getInstance().setCountries(countries);

                List<ProfileType> profileTypes=profileDefaults.getProfileTypes();
                SessionInfo.getInstance().setProfileTypes(profileTypes);

            }

            @Override
            public void onFailure(Call<ProfileDefaults> call, Throwable t) {

            }


        });
    }
}
