package com.samayu.sca.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samayu.sca.Application;
import com.samayu.sca.businessobjects.*;
import com.samayu.sca.dto.ProfileDefaultsDTO;
import com.samayu.sca.service.DataAccessService;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path="/global")
public class GlobalRest {

    @Qualifier("queueJmsTemplate")
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    DataAccessService dataAccessService;

    @GetMapping(path="/countries" )
    public ResponseEntity<Iterable<Country>> getAllCountries(){
        return ResponseEntity.ok(dataAccessService.findAllCountries());

    }

    @GetMapping(path="/cities/{countryId}")
    public ResponseEntity<Iterable<City>> getCities(@PathVariable(name="countryId" ) String countryId ) {
            return ResponseEntity.ok( dataAccessService.findCitiesByCountry( countryId ));
    }

    @GetMapping(path="/profileTypes")
    public ResponseEntity<Iterable<ProfileType>> getProfileTypes() {
        return ResponseEntity.ok( dataAccessService.findAllProfileTypes());
    }

    @GetMapping(path="/profileDefaults")
    public ResponseEntity<ProfileDefaultsDTO> getCountryAndProfiles(){

        ProfileDefaultsDTO dto = new ProfileDefaultsDTO();
        dto.setCountries( dataAccessService.findAllCountries());
        dto.setProfileTypes( dataAccessService.findAllProfileTypes() );
        return ResponseEntity.ok( dto );
    }

    @RequestMapping(path="/castingCall",method = RequestMethod.POST)
    public ResponseEntity<CastingCall> createCastingCall(
            @RequestParam("castingCallId") long castingCallId,
            @RequestParam("projectName") String projectName,
            @RequestParam("projectDetails") String projectDetails,
            @RequestParam("productionCompany") String productionCompany,
            @RequestParam("roleDetails") String roleDetails,
            @RequestParam("startAge") int startAge,
            @RequestParam("endAge") int endAge,
            @RequestParam("gender") int gender,
            @RequestParam("cityId") int cityId,
            @RequestParam("countryId") int countryId,
            @RequestParam("address") String address,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate")  String endDate,
            @RequestParam("hours") String hours,
            @RequestParam("userId") long userId,
            @RequestParam("roleIds") String[] roleIds
    ) throws JsonProcessingException {
        CastingCall castingCall = dataAccessService.createOrUpdateCastingCall(castingCallId, projectName, projectDetails,
                productionCompany, roleDetails, startAge, endAge, gender, cityId, countryId, address,
                LocalDate.parse(startDate), LocalDate.parse(endDate), hours, userId, roleIds);
        ObjectMapper objectMapper = new ObjectMapper();
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        jmsTemplate.convertAndSend(Application.CASTING_CALL_MESSAGE_QUEUE, objectMapper.writeValueAsString(castingCall));
        return ResponseEntity.ok(castingCall);
    }

}
