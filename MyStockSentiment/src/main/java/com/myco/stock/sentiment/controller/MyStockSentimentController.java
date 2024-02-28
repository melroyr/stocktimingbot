package com.myco.stock.sentiment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myco.stock.common.dto.FilteredNewsDTO;
import com.myco.stock.sentiment.domain.TickerNetSentiment;
import com.myco.stock.sentiment.service.MyStockSentimentService;

@RestController
@RequestMapping(path = "/stock")
public class MyStockSentimentController {

    @Autowired
    private MyStockSentimentService myStockSentimentService;

    @PostMapping(path = "/sentiment")
    public TickerNetSentiment processNews(@RequestBody FilteredNewsDTO filteredNewsDTO) throws Exception {

    	TickerNetSentiment tickerNetSentiment = myStockSentimentService.getTickerNetSentiment(filteredNewsDTO);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tickerNetSentiment);
        return tickerNetSentiment;
    }

}
