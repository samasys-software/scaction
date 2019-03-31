package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.User;
import  org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
     User findByFbUser(String  fbUser);
     List<User> findByCityId(int cityId);
     List<User> findBySearchable(boolean searchable);
}
