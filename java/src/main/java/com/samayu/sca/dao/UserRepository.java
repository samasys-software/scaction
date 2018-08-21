package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.User;
import  org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
public User findByFbUser(String  fbUser);
}
