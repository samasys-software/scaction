package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.Portfolio;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioDetailsRepository extends CrudRepository<Portfolio,Long>{
    Portfolio findByUserId(long userId);
}
