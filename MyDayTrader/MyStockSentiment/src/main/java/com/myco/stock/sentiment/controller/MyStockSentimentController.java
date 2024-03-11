package com.myco.stock.sentiment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myco.stock.common.dto.FilteredNewsDTO;
import com.myco.stock.common.dto.StockAdviceDTO;
import com.myco.stock.common.dto.TickerNetSentimentDTO;
import com.myco.stock.sentiment.domain.TickerNetSentiment;
import com.myco.stock.sentiment.service.MyStockSentimentService;

@RestController
@RequestMapping(path = "/stock")
public class MyStockSentimentController {

    @Autowired
    private MyStockSentimentService myStockSentimentService;

    @PostMapping(path = "/sentiment")
    public TickerNetSentimentDTO processNews(@RequestBody FilteredNewsDTO filteredNewsDTO) throws Exception {

    	TickerNetSentiment tickerNetSentiment = myStockSentimentService.getTickerNetSentiment(filteredNewsDTO);
    	TickerNetSentimentDTO tnsDTO = new TickerNetSentimentDTO();
    	tnsDTO.setEndDatetime(tickerNetSentiment.getEndDatetime());
    	tnsDTO.setId(tickerNetSentiment.getId());
    	tnsDTO.setNetSentiment(tickerNetSentiment.getNetSentiment());
    	tnsDTO.setStartDatetime(tickerNetSentiment.getStartDatetime());
    	tnsDTO.setTicker(tickerNetSentiment.getTicker());
    	
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tickerNetSentiment);
        
        StockAdviceDTO stockAdviceDTO = new StockAdviceDTO();
        stockAdviceDTO.setTicker(tickerNetSentiment.getTicker()); 
        stockAdviceDTO.setFunction("GLOBAL_QUOTE");
        stockAdviceDTO.setSentiment(tickerNetSentiment.getNetSentiment());
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8082/stocks", 
        		                    stockAdviceDTO,
        		                    String.class);
        
        return tnsDTO;
    }

}
