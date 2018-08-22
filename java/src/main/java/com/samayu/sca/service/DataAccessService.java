package com.samayu.sca.service;

import com.samayu.sca.businessobjects.User;
import com.samayu.sca.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DataAccessService {

    @Autowired
    UserRepository userRepository;

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
}
