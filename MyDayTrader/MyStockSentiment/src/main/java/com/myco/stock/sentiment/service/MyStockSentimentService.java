package com.myco.stock.sentiment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myco.stock.common.dto.FilteredNewsDTO;
import com.myco.stock.sentiment.domain.SentimentAnalyzer;
import com.myco.stock.sentiment.domain.SentimentResult;
import com.myco.stock.sentiment.domain.TickerNetSentiment;
import com.myco.stock.sentiment.domain.TickerSentiment;
import com.myco.stock.sentiment.repository.MyStockSentimentRepository;

@Service
public class MyStockSentimentService {
	
	@Autowired
	private MyStockSentimentRepository myStockSentimentRespository;
	
	private double netSentiment;
	
	public TickerNetSentiment getTickerNetSentiment(FilteredNewsDTO filteredNewsDTO) {
		
		netSentiment = 0.0;
		List<String> newsTitles = filteredNewsDTO.getNewsTitles();
		for(String newsTitle : newsTitles) {
			SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
			sentimentAnalyzer.initialize();
			SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(newsTitle);
			
			saveTickerSentiment(filteredNewsDTO, sentimentResult);
			
			processNetSentiment(sentimentResult.getSentimentScore(), sentimentResult.getSentimentType());
		}
		
		return saveTickerNetSentiment(filteredNewsDTO);
	}
	
	private TickerNetSentiment saveTickerNetSentiment(FilteredNewsDTO filteredNewsDTO) {
		TickerNetSentiment tickerNetSentiment = new TickerNetSentiment();
		tickerNetSentiment.setTicker(filteredNewsDTO.getTicker());
		tickerNetSentiment.setNetSentiment(netSentiment);
		tickerNetSentiment.setStartDatetime(filteredNewsDTO.getStartDate());
		tickerNetSentiment.setEndDatetime(filteredNewsDTO.getEndDate());
		
		return tickerNetSentiment;
	}
	
	private void processNetSentiment(double score, String type) {
		if (type.contains("Negative")) {
			netSentiment -= score;
		} else {
			netSentiment += score;
		}
	}
	
	private void saveTickerSentiment(FilteredNewsDTO filteredNewsDTO, SentimentResult sentimentResult) {
		TickerSentiment tickerSentiment = new TickerSentiment();
		tickerSentiment.setTicker(filteredNewsDTO.getTicker());
		tickerSentiment.setSentimentScore(sentimentResult.getSentimentScore());
		tickerSentiment.setSentimentType(sentimentResult.getSentimentType());
		tickerSentiment.setStartDatetime(filteredNewsDTO.getStartDate());
		tickerSentiment.setEndDatetime(filteredNewsDTO.getEndDate());
		
		myStockSentimentRespository.save(tickerSentiment);
	}
}
