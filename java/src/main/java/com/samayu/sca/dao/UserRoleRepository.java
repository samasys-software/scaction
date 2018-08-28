package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole,Long> {

    List<UserRole> findByUserId(long userId);
}
