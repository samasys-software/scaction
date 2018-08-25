package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.Country;
import org.springframework.data.repository.CrudRepository;


public interface CountryRepository extends CrudRepository<Country,Integer> {
}
