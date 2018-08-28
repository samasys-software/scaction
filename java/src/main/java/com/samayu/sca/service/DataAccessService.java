package com.samayu.sca.service;

import com.samayu.sca.businessobjects.*;
import com.samayu.sca.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
                          boolean searchable, int[] roles ){

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
            updateRoles( user , roles , true );
            userRepository.save(user);

        }
        return userRepository.findByFbUser(fbUser);
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
                            userRole.setCreateDate(LocalDateTime.now());
                            userRole.setUpdatedDate(LocalDateTime.now());
                            userRole.setRoleId(tmpRole);
                            userRole.setExpirationDate(LocalDate.now().plus(1, ChronoUnit.YEARS));
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
                            userRole.setCreateDate(LocalDateTime.now());
                            userRole.setUpdatedDate(LocalDateTime.now());
                            userRole.setRoleId( tmpRole );
                            userRole.setExpirationDate(LocalDate.now().plus(1, ChronoUnit.YEARS));
                            newRoles.add(userRole);
                        }

                    }

                    user.setUserRoles( userRoleRepository.findByUserId( user.getUserId() ));
                }

                if( newRoles.size() > 0 ) {
                    userRoleRepository.save(newRoles);
                }
    }

    public User update( String fbUser, String screenName, String name, String email ,
                          String countryCode,  String city, String phoneNumber,
                          String whatsappNumber, int gender, LocalDate dateOfBirth,
                          boolean searchable, int[] roles ){

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

            updateRoles( user , roles , false );
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
