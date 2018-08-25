package com.samayu.sca;

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
    public ResponseEntity<User> create(@RequestParam("fb_user") String fbUser, @RequestParam("fb_Email") String fbEmail,
                                 @RequestParam("profile_pic") String profile_pic){
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
