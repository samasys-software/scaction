package com.samayu.sca.service;

import com.samayu.sca.businessobjects.City;
import com.samayu.sca.businessobjects.Country;
import com.samayu.sca.businessobjects.User;
import com.samayu.sca.dao.CityRepository;
import com.samayu.sca.dao.CountryRepository;
import com.samayu.sca.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataAccessService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    public User register( String fbUser, String fbEmail, String profilePic){

        if( userRepository.findByFbUser( fbUser ) == null ) {
            User user = new User();
            user.setFbUser(fbUser);
            user.setFbEmail(fbEmail);
            user.setProfilePic(profilePic);
            userRepository.save(user);
        }
        return userRepository.findByFbUser(fbUser);
    }

    public User findUser(String fbUser ){
        return userRepository.findByFbUser( fbUser );
    }

    public Iterable<Country> findAllCountries(){
        return countryRepository.findAll();
    }

    public Iterable<City> findCitiesByCountry( String countryCode){
        return cityRepository.findByCountryCode( countryCode );
    }

}
