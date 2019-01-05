package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.PortfolioPicture;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PortfolioRepository extends CrudRepository<PortfolioPicture,Long> {
     List<PortfolioPicture> findByUserIdAndActive(long userId,boolean active );
}
