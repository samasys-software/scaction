package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.*;
import com.samayu.sca.dto.ProfileDefaultsDTO;
import com.samayu.sca.service.DataAccessService;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(path="/global")
public class GlobalRest {

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
            @RequestParam("projectName") String projectName,
            @RequestParam("projectDetails") String projectDetails,
            @RequestParam("productionCompany") String productionCompany,
            @RequestParam("roleDetails") String roleDetails,
            @RequestParam("startAge") int startAge,
            @RequestParam("endAge") int endAge,
            @RequestParam("gender") int gender,
            @RequestParam("cityName") String cityName,
            @RequestParam("countryName") String countryName,
            @RequestParam("address") String address,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate")  String endDate,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime
    ){
        CastingCall castingCall = dataAccessService.createOrUpdateCastingCall(projectName, projectDetails,
                productionCompany, roleDetails, startAge, endAge, gender, cityName, countryName, address,
                null, null, startTime, endTime);
        return ResponseEntity.ok(castingCall);
    }

}
