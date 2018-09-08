package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.CastingCall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CastingCallRepository extends CrudRepository<CastingCall, Long> {
    List<CastingCall> findByUserId(long userId);
    
}
