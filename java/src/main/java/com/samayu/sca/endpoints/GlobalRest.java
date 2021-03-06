package com.samayu.sca.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samayu.sca.Application;
import com.samayu.sca.businessobjects.*;
import com.samayu.sca.dto.ProfileDefaultsDTO;
import com.samayu.sca.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
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
  
    @GetMapping(path="/talentImages")
    public ResponseEntity<Iterable<User>> getTopProfiles(){
        return ResponseEntity.ok(dataAccessService.findUserImages());
    }

    @GetMapping(path = "/castingcalls")
    public ResponseEntity<Iterable<CastingCall>> getAllCastingCalls()  {
        return ResponseEntity.ok(dataAccessService.findAllCastingCalls());
    }

    @GetMapping(path = "/castingcallDetails/{castingCallId}")
    public ResponseEntity<CastingCall> getCastingCall(@PathVariable("castingCallId") long castingCallId,@RequestParam(name="userId",required = false) Long userId )  {

        CastingCall castingCall = dataAccessService.findCastingCall( castingCallId);

        if( userId != null ){
            List<CastingCallApplication> applications = dataAccessService.getCastingCallApplicationForUser( castingCallId , userId );

            if( applications != null && applications.size() > 0 ){
                castingCall.setUserApplications( applications );
            }
        }

        return ResponseEntity.ok( castingCall );
    }

    @GetMapping( path="/search/{pageNo}/{profilesPerPage}")
    public ResponseEntity<Iterable<User>> getActorProfiles(@PathVariable("pageNo") int pageNo, @PathVariable("profilesPerPage") int resultSize ){
            return ResponseEntity.ok(dataAccessService.getActorProfiles( pageNo , resultSize ));
    }



}
