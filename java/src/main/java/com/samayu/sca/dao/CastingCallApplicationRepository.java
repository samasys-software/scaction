package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.CastingCall;
import com.samayu.sca.businessobjects.CastingCallApplication;
import com.samayu.sca.businessobjects.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CastingCallApplicationRepository extends CrudRepository<CastingCallApplication,Long>{

    List<CastingCallApplication> findByCastingCallIdAndUser(long castingCallId, User user);
    CastingCallApplication findByCastingCallIdAndUserAndRoleId(long castingCallId, User user , int roleId);
    List<CastingCallApplication> findByCastingCallId(long castingCallId);
    List<CastingCallApplication> findByUser(User user);

}
