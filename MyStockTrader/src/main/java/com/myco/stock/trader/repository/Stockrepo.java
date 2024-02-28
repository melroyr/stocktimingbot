package com.myco.stock.trader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.AlphaVantageAPI.entity.APIData;

@Repository
public interface Stockrepo extends JpaRepository<APIData,Integer> {

}
