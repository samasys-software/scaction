package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City,Integer>{
    List<City> findByCountryCode(String countryId );
}
