package com.myco.stock.trader.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stock.AlphaVantageAPI.service.AlphaVantageService;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
    private  AlphaVantageService alphaVantageService;

	/*
	 * @Autowired public StockController(AlphaVantageService alphaVantageService) {
	 * this.alphaVantageService = alphaVantageService; }
	 */
	
    @GetMapping
    public ResponseEntity<String> getStockPrice(@RequestParam String symbol,@RequestParam String function) throws IOException, InterruptedException {
		String stockPrice = alphaVantageService.getStockPrice(symbol,function);
		
		return ResponseEntity.ok(stockPrice);
	}
}
