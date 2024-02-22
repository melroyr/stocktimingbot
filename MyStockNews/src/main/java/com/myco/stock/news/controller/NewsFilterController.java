package com.myco.stock.news.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myco.stock.news.entities.FilteredNews;
import com.myco.stock.news.service.IServiceFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/news")
public class NewsFilterController {

    @Autowired
    IServiceFilter serviceFilter;

    @GetMapping(path = "/filterNews")
    public FilteredNews getNews(@RequestParam String country, @RequestParam String category, @RequestParam String keyword) throws Exception {

    	FilteredNews filteredNews = serviceFilter.getRelevantNews(country,category,keyword);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(filteredNews);
        return filteredNews;
    }

}
