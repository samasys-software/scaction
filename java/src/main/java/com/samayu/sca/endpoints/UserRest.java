package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.User;
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
                 LocalDate.parse(dateOfBirth) , Boolean.parseBoolean(searchable) , roles );
        return ResponseEntity.ok(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
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


}
