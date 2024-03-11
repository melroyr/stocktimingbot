package com.myco.stock.trader.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myco.stock.common.dto.StockAdviceDTO;
import com.myco.stock.trader.service.MyStockTraderApplicationService;

@RestController
@RequestMapping("/stocks")
public class MyStockTraderApplicationController {

	@Autowired
    private  MyStockTraderApplicationService alphaVantageService;

	/*
	 * @Autowired public StockController(AlphaVantageService alphaVantageService) {
	 * this.alphaVantageService = alphaVantageService; }
	 */
	
    @PostMapping
    public ResponseEntity<String> stockAdvice(@RequestBody StockAdviceDTO stockAdviceDTO) throws IOException, InterruptedException {
		String stockPrice = alphaVantageService.getStockPrice(stockAdviceDTO.getTicker(),
															  stockAdviceDTO.getFunction(),
															  stockAdviceDTO.getSentiment());
		
		return ResponseEntity.ok(stockPrice);
	}
}
