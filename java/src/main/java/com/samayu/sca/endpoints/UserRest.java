package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.CastingCallApplication;
import com.samayu.sca.businessobjects.User;
import com.samayu.sca.businessobjects.UserNotification;
import com.samayu.sca.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Named;
import javax.ws.rs.*;
import java.time.LocalDate;


@RestController
@RequestMapping(path="/user/")
public class UserRest {

    @Autowired
    DataAccessService dataAccessService;


    @RequestMapping(path="/register",method = RequestMethod.POST)
    public ResponseEntity<User> create(
            @RequestParam("fbUser") String fbUser,
            @RequestParam("screenName") String screenName,
            @RequestParam("fbName") String name,
            @RequestParam("fbEmail") String fbEmail,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("cityId") String city,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("whatsappNumber") String whatsappNumber,
            @RequestParam("gender") String gender,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("searchable") String searchable,
            @RequestParam("profilePic") String profilePic,
            @RequestParam("roles") int[] roles
                                       ){

         User user = dataAccessService.register( fbUser, screenName , name , fbEmail ,
                 countryCode , city , phoneNumber , whatsappNumber, Integer.parseInt(gender) ,
                 LocalDate.parse(dateOfBirth) , Boolean.parseBoolean(searchable) , profilePic,roles );
        return ResponseEntity.ok(user);
    }

    @GetMapping(path="/checkUser/{fbUser}" )
    public ResponseEntity<User> checkUser(@PathVariable("fbUser") String fbUser ){
            User user = dataAccessService.findUser(fbUser);
            if( user != null ) {
                return ResponseEntity.ok(user);
            }
            else{
                return ResponseEntity.notFound().build();
            }
    }


    @GetMapping(path="/notifications/{userId}")
    public ResponseEntity<Iterable<UserNotification>> getNotificationsForUser(@PathVariable("userId") long userId ){
        return ResponseEntity.ok( dataAccessService.findNotificationsByUser( userId ));
    }

    @PostMapping(path="/applyForCastingCall")
    public ResponseEntity<Boolean> applyForCastingCall(@RequestParam("castingCallId") long castingCallId , @RequestParam("userId") long userId , @RequestParam("roleId") int roleId )
    {
        CastingCallApplication application = new CastingCallApplication();
        application.setCastingCallId( castingCallId );
        application.setUser( dataAccessService.findUser( userId ));
        application.setRoleId( roleId );
        return ResponseEntity.ok(dataAccessService.createCastingCallApplication( application ));

    }

    @RequestMapping(path="/createPortfolio",method = RequestMethod.POST)
    public ResponseEntity<Void> createPortfolio() {
        return null;
    }




}
