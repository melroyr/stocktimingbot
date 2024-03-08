package com.myco.stock.trader.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	
    @GetMapping
    public ResponseEntity<String> getStockPrice(@RequestParam String symbol,
    		@RequestParam String function,
    		@RequestParam int sentiment) throws IOException, InterruptedException {
		String stockPrice = alphaVantageService.getStockPrice(symbol,function, sentiment);
		
		return ResponseEntity.ok(stockPrice);
	}
}
