package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.User;
import org.springframework.data.jpa.repository.Query;
import  org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
     User findByFbUserAndLoginType(String  fbUser, int loginType);
     List<User> findByCityId(int cityId);

     @Query("select u from User u where portfolio is not null and searchable =1")
     List<User> findActors();

}
