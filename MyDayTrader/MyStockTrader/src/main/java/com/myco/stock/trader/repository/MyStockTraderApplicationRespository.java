package com.myco.stock.trader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myco.stock.trader.domain.StocTradeData;

@Repository
public interface MyStockTraderApplicationRespository extends JpaRepository<StocTradeData,Integer> {

}
