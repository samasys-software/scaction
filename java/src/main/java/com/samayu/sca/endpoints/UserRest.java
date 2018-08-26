package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.User;
import com.samayu.sca.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Named;
import javax.ws.rs.*;


@RestController
@RequestMapping(path="/user/")
public class UserRest {

    @Autowired
    DataAccessService dataAccessService;


    @RequestMapping(path="/register",method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestParam("fbUser") String fbUser,
                                       @RequestParam("fbEmail") String fbEmail,
                                       @RequestParam("fbProfilePic") String profile_pic,
                                       @RequestParam("fbName") String name,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("countryCode") String countryCode,
                                       @RequestParam("whatsappNumber") String whatsappNumber,
                                       @RequestParam("screenName") String screen){
         User user = dataAccessService.register(fbUser,fbEmail,profile_pic);
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
