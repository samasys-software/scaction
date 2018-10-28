package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.CastingCall;

import com.samayu.sca.businessobjects.CastingCallApplication;
import com.samayu.sca.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(path="/coordinator")
public class CoordinatorRest {

    @Autowired
    DataAccessService dataAccessService;

    @GetMapping(path = "/castingcalls/{userId}")
    public ResponseEntity<List<CastingCall>> getMyCastingCalls(@PathVariable(value = "userId" ) long userId) {
        return ResponseEntity.ok(dataAccessService.findCastingCallsByUser(userId));
    }

    @GetMapping(path="/castingCallApplications/{castingCallId}")
    public ResponseEntity<List<CastingCallApplication>> getCastingCallApplications(@PathVariable(value="castingCallId") long castingCallId ){
        return ResponseEntity.ok( dataAccessService.getCastingCallApplications( castingCallId));
    }

}
