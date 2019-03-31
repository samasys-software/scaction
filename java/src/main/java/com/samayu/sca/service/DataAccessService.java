package com.samayu.sca.service;

import com.samayu.sca.businessobjects.*;
import com.samayu.sca.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Port;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    private CastingCallRepository castingCallRepository;

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private CastingCallApplicationRepository applicationRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioDetailsRepository portfolioDetailsRepository;

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
                Iterable<ProfileType> profileTypes = profileTypeRepository.findAll();
                Map<Integer,ProfileType> profileMap = new HashMap<>();
                profileTypes.forEach( (profileType) -> {
                    profileMap.put( profileType.getId() , profileType );
                });
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

                            userRole.setRoleType( profileMap.get( tmpRole ));

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
                        int roleId = role.getRoleType().getId();

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
                           if( role.getRoleType().getId() == tmpRole ){
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
                            userRole.setRoleType( profileMap.get( tmpRole ) );
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
        if( user != null ) {
            try {
                user.setUserRoles(userRoleRepository.findByUserId(user.getUserId()));
            }
            catch(Exception er){
                er.printStackTrace();
            }
        }
        return user;
    }

    public User findUser(long userId ){
        return userRepository.findOne( userId );
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

    public Iterable<CastingCall> findAllCastingCalls(){
        return castingCallRepository.findAll();
    }

    public CastingCall createOrUpdateCastingCall(long castingCallId, String projectName,
                                                 String projectDetails, String productionCompany,
                                                 String roleDetails, int startAge,
                                                 int endAge, int gender, int cityId,
                                                 int countryId, String address,
                                                 LocalDate startDate, LocalDate endDate,
                                                 String hours, long userId, String[] roleIds){
        StringBuilder role = new StringBuilder();
        CastingCall castingCall = new CastingCall();
        if (castingCallId != -1){
            castingCall.setId(castingCallId);
        }
        castingCall.setProjectName(projectName);
        castingCall.setProjectDetails(projectDetails);
        castingCall.setProductionCompany(productionCompany);
        castingCall.setRoleDetails(roleDetails);
        castingCall.setStartAge(startAge);
        castingCall.setEndAge(endAge);
        castingCall.setGender(gender);
        castingCall.setCityId(cityId);
        castingCall.setCountryId(countryId);
        castingCall.setAddress(address);
        castingCall.setStartDate(Date.valueOf(startDate));
        castingCall.setEndDate(Date.valueOf(endDate));
        castingCall.setHours(hours);
        castingCall.setUserId(userId);
        for (String roleId : roleIds) {
            role.append(roleId);
            role.append(",");
        }
        castingCall.setRoleIds(role.toString());
        castingCallRepository.save(castingCall);
        return castingCall;
    }
  
    public Iterable<User> findUserImages(){
        return userRepository.findAll();
    }

    public List<CastingCall> findCastingCallsByUser(long userId ){
        return castingCallRepository.findByUserId( userId );
    }

    public Iterable<UserNotification> findNotificationsByUser(long userId ){
        return userNotificationRepository.findByUserId( userId );
    }

    public CastingCall findCastingCall(long castingCallId){
        return castingCallRepository.findOne(castingCallId);
    }

    public boolean createCastingCallApplication(CastingCallApplication application){
        boolean found = false;

        CastingCallApplication existingApplication = applicationRepository.findByCastingCallIdAndUserAndRoleId(application.getCastingCallId(), application.getUser(), application.getRoleId());

        if( existingApplication != null ) {
            found = true;
        }

        if( !found ) {
            application.setCreateDate( new java.sql.Timestamp(System.currentTimeMillis()));
            applicationRepository.save(application);
        }

        return !found;
    }

    public List<CastingCallApplication> getCastingCallApplicationForUser(long castingCallId , long userId )
    {
        return applicationRepository.findByCastingCallIdAndUser( castingCallId , findUser( userId ) );
    }

    public List<CastingCallApplication> getCastingCallApplications(long castingCallId )
    {
        return applicationRepository.findByCastingCallId( castingCallId );
    }

    public List<PortfolioPicture> getPortfolioPicsForUser(long userId ){

        return portfolioRepository.findByUserIdAndActive( userId , true );
    }

    public void savePortfolioPicture(long userId , String extension , String filename , int type ){
        PortfolioPicture picture = new PortfolioPicture();
        picture.setActive(true );
        picture.setUserId( userId );
        picture.setExtension( extension );
        picture.setFileName( filename );
        picture.setType( type );

        portfolioRepository.save( picture );

    }

    public void deletePortfolioPicture(long userId, long portfolioId ){
        PortfolioPicture picture = portfolioRepository.findOne( portfolioId );
        if( picture.getUserId() == userId ){
            picture.setActive(false);
            portfolioRepository.save(picture );
        }
    }

    public Iterable<User> getActorProfiles(int pageNo , int resultSize){

        return userRepository.findActors();

    }

    public Portfolio updatePortfolio(Portfolio portfolio){

        Portfolio existingPortfolio = portfolioDetailsRepository.findByUserId( portfolio.getUserId() );

        if( existingPortfolio == null )
        {
            portfolioDetailsRepository.save( portfolio );
            User user = userRepository.findOne( portfolio.getUserId() );
            user.setPortfolio( portfolio );
            userRepository.save( user );
            return portfolio;
        }
        else{
            existingPortfolio.setShortDescription( portfolio.getShortDescription());
            existingPortfolio.setAreaOfExpertise( portfolio.getAreaOfExpertise() );
            existingPortfolio.setMessage( portfolio.getMessage() );
            existingPortfolio.setProjectsWorked( portfolio.getProjectsWorked() );
            portfolioDetailsRepository.save( existingPortfolio );

            return existingPortfolio;
        }


    }

    public Portfolio getPortfolio(long userId ){
        return portfolioDetailsRepository.findByUserId( userId );
    }

}
