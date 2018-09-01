package com.samayu.sca.endpoints;

import com.samayu.sca.businessobjects.City;
import com.samayu.sca.businessobjects.Country;
import com.samayu.sca.businessobjects.ProfileType;
import com.samayu.sca.businessobjects.User;
import com.samayu.sca.dto.ProfileDefaultsDTO;
import com.samayu.sca.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
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

    @GetMapping(path="/talentImages")
    public ResponseEntity<Iterable<User>> getTopProfiles(){
        return ResponseEntity.ok(dataAccessService.findUserImages());
    }

}
