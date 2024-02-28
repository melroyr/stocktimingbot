package com.myco.stock.sentiment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myco.stock.sentiment.domain.TickerSentiment;

public interface MyStockSentimentRepository extends JpaRepository<TickerSentiment, Long> {

}
