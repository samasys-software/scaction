package com.samayu.sca.service;

import com.samayu.sca.businessobjects.City;
import com.samayu.sca.businessobjects.Country;
import com.samayu.sca.businessobjects.ProfileType;
import com.samayu.sca.businessobjects.User;
import com.samayu.sca.dao.CityRepository;
import com.samayu.sca.dao.CountryRepository;
import com.samayu.sca.dao.ProfileTypeRepository;
import com.samayu.sca.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataAccessService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ProfileTypeRepository profileTypeRepository;

    public User register( String fbUser, String screenName, String name, String email ,
                          String countryCode,  String city, String phoneNumber,
                          String whatsappNumber, int gender, LocalDate dateOfBirth,
                          boolean searchable, String[] roles ){

        if( userRepository.findByFbUser( fbUser ) == null ) {
            User user = new User();
            user.setFbUser( fbUser );
            user.setScreenName( screenName );
            user.setFbName( name );
            user.setFbEmail( email );
            user.setCountryCode( countryCode );
            user.setCity( city );
            user.setPhoneNumber( phoneNumber );
            user.setWhatsappNumber( whatsappNumber );
            user.setGender( gender );
            user.setDateOfBirth( dateOfBirth );
            user.setSearchable( searchable );
            userRepository.save(user);
        }
        return userRepository.findByFbUser(fbUser);
    }

    public User update( String fbUser, String screenName, String name, String email ,
                          String countryCode,  String city, String phoneNumber,
                          String whatsappNumber, int gender, LocalDate dateOfBirth,
                          boolean searchable, String[] roles ){

        User user = userRepository.findByFbUser( fbUser );
        if(  user != null ) {
            user.setScreenName( screenName );
            user.setFbName( name );
            user.setFbEmail( email );
            user.setCountryCode( countryCode );
            user.setCity( city );
            user.setPhoneNumber( phoneNumber );
            user.setWhatsappNumber( whatsappNumber );
            user.setGender( gender );
            user.setDateOfBirth( dateOfBirth );
            user.setSearchable( searchable );
            userRepository.save(user);
            return user;
        }
        return null;
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

    public Iterable<ProfileType> findAllProfileTypes(){
        return profileTypeRepository.findAll();
    }

}
