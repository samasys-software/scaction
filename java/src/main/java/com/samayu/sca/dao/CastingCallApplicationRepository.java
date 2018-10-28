package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.CastingCallApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CastingCallApplicationRepository extends CrudRepository<CastingCallApplication,Long>{

    List<CastingCallApplication> findByCastingCallIdAndUserId(long castingCallId, long userId);
    CastingCallApplication findByCastingCallIdAndUserIdAndRoleId(long castingCallId, long userId , int roleId);
}
