package com.samayu.sca.service;

import com.samayu.sca.businessobjects.*;
import com.samayu.sca.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.LinkedList;
import java.util.List;

@Component
public class DataAccessService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ProfileTypeRepository profileTypeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User register( String fbUser, String screenName, String name, String email ,
                          String countryCode,  String city, String phoneNumber,
                          String whatsappNumber, int gender, LocalDate dateOfBirth,
                          boolean searchable, String profilePic, int[] roles ){

        User user = null;

        user = userRepository.findByFbUser( fbUser );
        if( user == null ) {
            user = new User();
        }

        user.setFbUser( fbUser );
        user.setScreenName( screenName );
        user.setFbName( name );
        user.setProfilePic( profilePic );
        user.setFbEmail( email );
        user.setCountryCode( countryCode );
        user.setCityId( Integer.parseInt(city) );
        user.setPhoneNumber( phoneNumber );
        user.setWhatsappNumber( whatsappNumber );
        user.setGender( gender );
        user.setDateOfBirth( Date.valueOf( dateOfBirth ) );
        user.setSearchable( searchable );
        user.setCreateDt( new Timestamp( System.currentTimeMillis() ));
        user.setUpdateDt( new Timestamp( System.currentTimeMillis() ));
        userRepository.save(user);
        updateRoles( user , roles , true );


        return findUser(fbUser);
    }

    private void updateRoles(User user, int[] roles, boolean newRegistration ){
                List<UserRole> userRoles = userRoleRepository.findByUserId( user.getUserId() );
                List<UserRole> newRoles = new LinkedList<>();

                if( newRegistration ) {
                    if (userRoles == null || userRoles.size() == 0) {
                        for (int tmpRole : roles) {
                            UserRole userRole = new UserRole();
                            userRole.setUserId( user.getUserId() );
                            userRole.setActive(true);
                            userRole.setCreateDate(new Timestamp(System.currentTimeMillis()));
                            userRole.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                            userRole.setRoleId(tmpRole);

                            userRole.setExpirationDate(new Date( System.currentTimeMillis()+365*24*60*60*1000 ));
                            newRoles.add(userRole);
                        }
                    }

                    user.setUserRoles( newRoles );
                }
                else {

                    /*
                    Now Checking if the Database UserRoles exist in the roles list
                    if it is not found then make it inactive.
                     */
                    for (UserRole role : userRoles) {
                        int roleId = role.getRoleId();

                        boolean isFound = false;

                        for (int tmpRole: roles ) {
                            if ( tmpRole == roleId) {
                                isFound = true;
                                break;
                            }
                        }

                        if (!isFound) {
                            if( role.isActive() ) {
                                role.setActive(false);
                                newRoles.add(role);
                            }
                        }
                    }

                    /*
                    Now find new roles
                     */

                    for (int tmpRole : roles ) {

                        boolean isFound = false;

                        for (UserRole role : userRoles) {
                           if( role.getRoleId() == tmpRole ){
                               isFound = true;
                               break;
                           }
                        }

                        if( !isFound ){
                            UserRole userRole = new UserRole();
                            userRole.setUserId( user.getUserId() );
                            userRole.setActive(true);
                            userRole.setCreateDate( new Timestamp( System.currentTimeMillis() ));
                            userRole.setUpdatedDate(new Timestamp( System.currentTimeMillis() ));
                            userRole.setRoleId( tmpRole );
                            userRole.setExpirationDate(new Date( System.currentTimeMillis()+365*24*60*60*1000 ));
                            newRoles.add(userRole);
                        }

                    }

                    user.setUserRoles( userRoleRepository.findByUserId( user.getUserId() ));
                }

                if( newRoles.size() > 0 ) {
                    userRoleRepository.save(newRoles);
                }
    }

    public User findUser(String fbUser ){
        User user = userRepository.findByFbUser( fbUser );
        user.setUserRoles( userRoleRepository.findByUserId( user.getUserId() ));
        return user;
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
