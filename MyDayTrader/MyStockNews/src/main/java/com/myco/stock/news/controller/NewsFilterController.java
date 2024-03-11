package com.myco.stock.news.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myco.stock.news.entities.FilteredArticle;
import com.myco.stock.news.entities.FilteredNews;
import com.myco.stock.news.service.IServiceFilter;
import com.myco.stock.common.dto.FilteredNewsDTO;
import com.myco.stock.common.dto.TickerNetSentimentDTO;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/news")
public class NewsFilterController {

    @Autowired
    IServiceFilter serviceFilter;

    @GetMapping(path = "/filterNews")
    public FilteredNews getNews(@RequestParam String ticker, 
    		@RequestParam String keyword) throws Exception {

    	FilteredNews filteredNews = serviceFilter.getRelevantNews(ticker, keyword);
    	
    	FilteredNewsDTO fnDTO = new FilteredNewsDTO();
    	fnDTO.setEndDate(filteredNews.getEndDatetime());
    	
    	//ArrayList<FilteredArticle> filteredArticles = filteredNews.getFilteredArticles();
    	ArrayList<String> descs = new ArrayList<>();
    	ArrayList<String> titles = new ArrayList<>();
    	for(FilteredArticle fa : filteredNews.getFilteredArticles()) {
    		descs.add(fa.getDescription());
    		titles.add(fa.getTitle());
    	}
    	
    	fnDTO.setNewsDescs(descs);
    	fnDTO.setNewsTitles(titles);
    	fnDTO.setStartDate(filteredNews.getStartDatetime());
    	fnDTO.setTicker(filteredNews.getTicker());
    	
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(filteredNews);
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8081/stock/sentiment", fnDTO, TickerNetSentimentDTO.class);
        
        return filteredNews;
    }

}
